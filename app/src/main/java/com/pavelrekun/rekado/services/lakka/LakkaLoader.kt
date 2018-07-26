package com.pavelrekun.rekado.services.lakka

import android.content.Context
import android.hardware.usb.*
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.logs.LogHelper.ERROR
import com.pavelrekun.rekado.services.logs.LogHelper.INFO
import com.pavelrekun.rekado.services.usb.USBHandler
import com.pavelrekun.rekado.services.utils.BinaryUtils
import com.pavelrekun.rekado.services.utils.Utils
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

class LakkaLoader : USBHandler {

    companion object {
        init {
            System.loadLibrary("lakka_launcher")
        }

        private const val SOURCE_BASE = 0x4000fc84
        private const val TARGET = SOURCE_BASE - 0xc - 2 * 4 - 2 * 4
        private const val DESTINATION_BASE = 0x40009000
        private const val OVERRIDE_LENGTH = TARGET - DESTINATION_BASE
        private const val PAYLOAD_BASE = 0x40010000
    }

    private val context = RekadoApplication.instance.applicationContext

    private lateinit var usbConnection: UsbDeviceConnection
    private lateinit var usbInterface: UsbInterface
    private lateinit var startEndpoint: UsbEndpoint
    private lateinit var endEndpoint: UsbEndpoint
    private lateinit var usbManager: UsbManager

    override fun handleDevice(device: UsbDevice) {
        usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        usbInterface = device.getInterface(0)

        startEndpoint = usbInterface.getEndpoint(0)
        endEndpoint = usbInterface.getEndpoint(1)

        usbConnection = usbManager.openDevice(device)

        claimInterface()

        readInitMessage()

        sanityCheck(SOURCE_BASE, DESTINATION_BASE)

        val header = ByteArray(4 + 0x2a4)
        BinaryUtils.writeInt32(header, 0, 0x30008)
        write(header, 0, header.size)

        val xferLength = 0x1000

        val payload = ByteArrayOutputStream()
        val buffer = ByteArray(0x1a3a * 4)
        payload.write(buffer)
        var entry = PAYLOAD_BASE + payload.size() + 4
        entry = entry or 1
        BinaryUtils.writeInt32(buffer, 0, entry)
        payload.write(buffer, 0, 4)
        readAdditionalFile(LakkaHelper.PAYLOAD_FILENAME, payload)
        val payloadData = payload.toByteArray()

        var i = 0
        while (i < payloadData.size) {
            write(payloadData, i, Math.min(xferLength, payloadData.size - i))
            i += xferLength
        }

        try {
            sanityCheck(SOURCE_BASE, DESTINATION_BASE)
        } catch (e: RuntimeException) {
            LogHelper.log(INFO, "Adding more data!")
            val data = ByteArray(xferLength)
            write(data, 0, data.size)
        }

        LogHelper.log(INFO, "Triggering Lakka!")

        nativeControlReadUnbounded(usbConnection.fileDescriptor, OVERRIDE_LENGTH)

        val tempBuffer = ByteArray(4096)
        while (true) {
            val length = usbConnection.bulkTransfer(startEndpoint, tempBuffer, tempBuffer.size, 0)

            if (length < 0) {
                continue
            }

            val cmd = String(tempBuffer, 0, length).trim()
            LogHelper.log(INFO, "Entering $cmd")

            if (cmd == "CBFS") {
                cbfs()
                break
            }

        }
    }

    private fun claimInterface() {
        usbConnection.claimInterface(usbInterface, true)
    }

    override fun releaseDevice() {
        usbConnection.releaseInterface(usbInterface)
    }

    @Throws(IOException::class)
    private fun readAdditionalFile(name: String, outputStream: OutputStream) {
        val inputStream = context.assets.open(name)
        inputStream.copyTo(outputStream, 4096)
    }

    private fun write(data: ByteArray, offset: Int, length: Int) {
        val ret = usbConnection.bulkTransfer(endEndpoint, data, offset, length, 0)
        if (ret < length) {
            LogHelper.log(ERROR, "Write failed (ret = $ret, expected = $length)!")
        }
    }

    private fun readInitMessage() {
        val data = ByteArray(0x10)
        val length = usbConnection.bulkTransfer(startEndpoint, data, data.size, 20)

        if (length >= 0) {
            LogHelper.log(INFO, "Device ID: ${Utils.bytesToHex(data)}")
        } else {
            LogHelper.log(ERROR, "Failed to get Device ID!")
        }
    }

    private fun sanityCheck(sourceBase: Int, destinationBase: Int) {
        val buffer = ByteArray(0x1000)
        val length = usbConnection.controlTransfer(0x82, 0, 0, 0, buffer, buffer.size, 0)

        if (length != 0x1000) {
            LogHelper.log(ERROR, "Failed to read length: $length!")
        }

        val currentSource = BinaryUtils.readInt32(buffer, 0xc)
        val currentDestination = BinaryUtils.readInt32(buffer, 0x14)

        if (currentSource != sourceBase || currentDestination != destinationBase) {
            throw RuntimeException("Sanity check failed (current source = $currentSource, current destination = $currentDestination)!")
        }
    }

    @Throws(IOException::class)
    private fun cbfs() {
        val dataStream = ByteArrayOutputStream()
        readAdditionalFile(LakkaHelper.COREBOOT_FILENAME, dataStream)

        val data = dataStream.toByteArray()

        if (data.size < 20 * 1024) {
            LogHelper.log(ERROR, "Invalid coreboot.rom!")
        }

        val inBuffer = ByteArray(8)

        while (true) {
            val inLength = usbConnection.bulkTransfer(startEndpoint, inBuffer, 8, 0)
            if (inLength < 8) {
                LogHelper.log(ERROR, "Failed to read coreboot.rom!")
            }

            var offset = BinaryUtils.readInt32BE(inBuffer, 0)
            var length = BinaryUtils.readInt32BE(inBuffer, 4)

            if (offset + length == 0) {
                return
            }

            LogHelper.log(INFO, "Sent 0x${length.toString(16)} bytes")

            while (length > 0) {
                var tempLength = length

                if (tempLength > 32 * 1024) {
                    tempLength = 32 * 1024
                }

                val ret = usbConnection.bulkTransfer(endEndpoint, data, offset, tempLength, 0)

                if (ret < 0) {
                    LogHelper.log(ERROR, "Failed to transfer $ret!")
                }

                offset += ret
                length -= ret
            }

        }
    }

    private external fun nativeControlReadUnbounded(fd: Int, size: Int)
}