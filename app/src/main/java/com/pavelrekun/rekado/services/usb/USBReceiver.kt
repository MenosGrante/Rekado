package com.pavelrekun.rekado.services.usb

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.widget.Toast
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.logs.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.Utils

class USBReceiver : BaseActivity() {

    private val APX_VID = 0x0955
    private val APX_PID = 0x7321

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action == UsbManager.ACTION_USB_ACCESSORY_ATTACHED) {
            val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)

            val vid = device.vendorId
            val pid = device.productId

            var usbHandler: USBHandler? = null

            Logger.log(1, "USB device connected: ${device.deviceName}")

            PayloadHelper.putChosenPayload(PayloadHelper.getPayloads()[0])

            if (vid == APX_VID && pid == APX_PID) {
                usbHandler = USBLoader()
            }

            Toast.makeText(this, device.deviceName, Toast.LENGTH_SHORT).show()

            usbHandler?.handleDevice(device)

            Logger.log(1, "Payload loading finished for device: " + device.deviceName)

            finish()
        }
    }
}