package com.pavelrekun.rekado.services.usb

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.lakka.LakkaLoader
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.logs.LogHelper.ERROR
import com.pavelrekun.rekado.services.logs.LogHelper.INFO
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.payloads.PayloadLoader
import com.pavelrekun.rekado.services.utils.SettingsUtils
import com.pavelrekun.rekado.services.utils.SwitchUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class USBReceiver : BaseActivity() {

    private lateinit var device: UsbDevice

    private var usbHandler: USBHandler? = null

    private lateinit var injectorChooserDialog: AlertDialog
    private lateinit var payloadChooserDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

            LogHelper.log(INFO, "USB device connected: ${device.deviceName}")

            if (SettingsUtils.checkAutoInjectorEnabled()) {
                PayloadHelper.putChosen(PayloadHelper.find(SettingsUtils.getAutoInjectorPayload())!!)
                injectPayload()
            } else {
                injectorChooserDialog = Dialogs.showInjectorSelectorDialog(this)
            }
        }
    }

    private fun injectPayload() {
        if (SwitchUtils.isRCM(device)) {
            usbHandler = PayloadLoader()
        }

        usbHandler?.handleDevice(device)

        LogHelper.log(INFO, "Payload loading finished for device: ${device.deviceName}")

        finishReceiver()
    }

    private fun injectLakka() {
        try {
            if (SwitchUtils.isRCM(device)) {
                usbHandler = LakkaLoader()
                usbHandler?.handleDevice(device)
                LogHelper.log(INFO, "Lakka loading finished for device: ${device.deviceName}")
            }
        } catch (t: Throwable) {
            LogHelper.log(ERROR, "Failed to inject Lakka!")
        }

        finishReceiver()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.PayloadSelected) {
        injectPayload()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.PayloadNotSelected) {
        finishReceiver()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.InjectorMethodNotSelected) {
        finishReceiver()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: Events.InjectorMethodPayloadSelected) {
        payloadChooserDialog = Dialogs.showPayloadsDialog(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.InjectorMethodLakkaSelected) {
        injectLakka()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun finishReceiver() {
        if (this::injectorChooserDialog.isInitialized && injectorChooserDialog.isShowing) {
            injectorChooserDialog.dismiss()
        }

        if (this::payloadChooserDialog.isInitialized && payloadChooserDialog.isShowing) {
            payloadChooserDialog.dismiss()
        }

        usbHandler?.releaseDevice()
        usbHandler = null
        finish()
    }
}