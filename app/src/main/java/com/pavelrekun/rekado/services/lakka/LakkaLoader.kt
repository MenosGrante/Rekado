package com.pavelrekun.rekado.services.lakka

import android.content.Context
import android.hardware.usb.*
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.usb.USBHandler
import com.pavelrekun.rekado.services.utils.Utils
import java.io.*

class LakkaLoader : USBHandler {

    companion object {
        init {
            System.loadLibrary("switchlauncher")
        }
    }

    private val context = RekadoApplication.instance.applicationContext

    private lateinit var usbConnection: UsbDeviceConnection
    private lateinit var usbInterface: UsbInterface
    private lateinit var startEndpoint: UsbEndpoint
    private lateinit var endEndpoint: UsbEndpoint
    private lateinit var usbManager: UsbManager

    override fun handleDevice(device: UsbDevice) {
        val sourceBase = 0x4000fc84
        val target = sourceBase - 0xc - 2 * 4 - 2 * 4
        val destinationBase = 0x40009000
        val overrideLength = target - destinationBase
        val payloadBase = 0x40010000

        usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        usbInterface = device.getInterface(0)

        startEndpoint = usbInterface.getEndpoint(0)
        endEndpoint = usbInterface.getEndpoint(1)

        usbConnection = usbManager.openDevice(device)

        readInitMessage()

        sanityCheck(sourceBase, destinationBase)

        val header = ByteArray(4 + 0x2a4)
        Utils.writeInt32(header, 0, 0x30008)
        write(header, 0, header.size)

        val xferLength = 0x1000

        val payload = ByteArrayOutputStream()
        val buffer = ByteArray(0x1a3a * 4)
        payload.write(buffer)
        var entry = payloadBase + payload.size() + 4
        entry = entry or 1
        Utils.writeInt32(buffer, 0, entry)
        payload.write(buffer, 0, 4)
        readAdditionalFile(LakkaHelper.PAYLOAD_FILENAME, payload)
        val payloadData = payload.toByteArray()

        var i = 0
        while (i < payloadData.size) {
            write(payloadData, i, Math.min(xferLength, payloadData.size - i))
            i += xferLength
        }

        try {
            sanityCheck(sourceBase, destinationBase)
        } catch (e: RuntimeException) {
            LogHelper.log(0, "Throwing more data!")
            val data = ByteArray(xferLength)
            write(data, 0, data.size)
        }

        LogHelper.log(1, "Performing HAX!")

        nativeControlReadUnbounded(usbConnection.fileDescriptor, overrideLength)

        val tempBuffer = ByteArray(4096)
        while (true) {
            val length = usbConnection.bulkTransfer(startEndpoint, tempBuffer, tempBuffer.size, 0)

            if (length < 0) {
                continue
            }

            val cmd = String(tempBuffer, 0, length).trim()
            LogHelper.log(1, "In $cmd")

            if (cmd == "CBFS") {
                cbfs()
                LogHelper.log(1, "You have been served!")
                break
            }

        }
    }

    fun claimInterface() {
        usbConnection.claimInterface(usbInterface, true)
    }

    fun releaseInterface() {
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
            LogHelper.log(0, "Write failed (ret = $ret, expected = $length)")
        }
    }

    private fun readInitMessage() {
        val data = ByteArray(0x10)
        val length = usbConnection.bulkTransfer(startEndpoint, data, data.size, 20)

        if (length >= 0) {
            LogHelper.log(1, "Init message ${Utils.bytesToHex(data)}")
        } else {
            LogHelper.log(0, "No init message!")
        }
    }

    private fun sanityCheck(sourceBase: Int, destinationBase: Int) {
        val buffer = ByteArray(0x1000)
        val length = usbConnection.controlTransfer(0x82, 0, 0, 0, buffer, buffer.size, 0)

        if (length != 0x1000) {
            LogHelper.log(0, "Reading error!")
        }

        val currentSource = Utils.readInt32(buffer, 0xc)
        val currentDestination = Utils.readInt32(buffer, 0x14)

        if (currentSource != sourceBase || currentDestination != destinationBase) {
            LogHelper.log(0, "Sanity check failed (current source = $currentSource, current destination = $currentDestination")
            throw RuntimeException("Sanity check failed (current source = $currentSource, current destination = $currentDestination")
        }
    }

    @Throws(IOException::class)
    private fun cbfs() {
        val dataStream = ByteArrayOutputStream()
        readAdditionalFile(LakkaHelper.COREBOOT_FILENAME, dataStream)

        val data = dataStream.toByteArray()

        if (data.size < 20 * 1024) {
            LogHelper.log(0, "Invalid coreboot.rom")
        }

        val inBuffer = ByteArray(8)

        while (true) {
            val inLength = usbConnection.bulkTransfer(startEndpoint, inBuffer, 8, 0)
            if (inLength < 8) {
                LogHelper.log(0, "Reading error!")
            }

            var offset = Utils.readInt32BE(inBuffer, 0)
            var length = Utils.readInt32BE(inBuffer, 4)

            if (offset + length == 0) {
                return
            }

            LogHelper.log(1, "Sending 0x${length.toString(16)} bytes @0x${offset.toString(16)}")

            while (length > 0) {
                var tempLength = length

                if (tempLength > 32 * 1024) {
                    tempLength = 32 * 1024
                }

                val ret = usbConnection.bulkTransfer(endEndpoint, data, offset, tempLength, 0)

                if (ret < 0) {
                    LogHelper.log(0, "Transfer error = $ret")
                }

                offset += ret
                length -= ret
            }

        }
    }

    private external fun nativeControlReadUnbounded(fd: Int, size: Int)
}