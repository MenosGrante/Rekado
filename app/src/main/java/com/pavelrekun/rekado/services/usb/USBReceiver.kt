package com.pavelrekun.rekado.services.usb

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.logs.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.payloads.PayloadLoader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class USBReceiver : BaseActivity() {

    companion object {
        private const val APX_VID = 0x0955
        private const val APX_PID = 0x7321
    }

    private var vid: Int = 0
    private var pid: Int = 0

    private lateinit var device: UsbDevice
    private var usbHandler: USBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

            vid = device.vendorId
            pid = device.productId

            Logger.log(1, "USB device connected: ${device.deviceName}")

            Dialogs.showPayloadsDialog(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: Events.PayloadSelected) {
        if (vid == APX_VID && pid == APX_PID) {
            usbHandler = PayloadLoader()
        }

        usbHandler?.handleDevice(device)

        Logger.log(1, "Payload loading finished for device: " + device.deviceName)

        PayloadHelper.removeChosenPayload()

        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: Events.PayloadNotSelected) {
        finish()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}