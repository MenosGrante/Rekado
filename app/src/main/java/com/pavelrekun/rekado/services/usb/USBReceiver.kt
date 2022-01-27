package com.pavelrekun.rekado.services.usb

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.handlers.PayloadsHandler
import com.pavelrekun.rekado.services.handlers.PreferencesHandler
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class USBReceiver : AppCompatActivity() {

    @Inject
    lateinit var usbPayloadLoader: USBPayloadLoader

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    @Inject
    lateinit var payloadsHandler: PayloadsHandler

    private lateinit var device: UsbDevice

    private lateinit var payloadChooserDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)!!

            LoginUtils.info("USB device connected: ${device.deviceName}")

            if (preferencesHandler.checkAutoInjectorEnabled()) {
                val defaultPayload = payloadsHandler.getAllPayloads().first().title
                injectPayload(payloadsHandler.find(preferencesHandler.getAutoInjectorPayload(defaultPayload)))
            } else {
                if (payloadsHandler.checkPayloadsExists()) {
                    payloadChooserDialog = DialogsShower.showPayloadsDialog(this, payloadsHandler, ::injectPayload, ::finishReceiver)
                } else {
                    DialogsShower.showNoPayloadsDialog(this, ::finishReceiver)
                }

            }
        }
    }

    private fun injectPayload(payload: Payload) {
        if (Utils.isRCM(device)) {
            usbPayloadLoader.handleDevice(payload, device)
            LoginUtils.info("Payload loading finished for device: ${device.deviceName}")
        } else {
            LoginUtils.info("${device.deviceName} is not RCM device! Aborting injection.")
        }

        finishReceiver()
    }

    private fun finishReceiver() {
        if (this::payloadChooserDialog.isInitialized && payloadChooserDialog.isShowing) {
            payloadChooserDialog.dismiss()
        }

        usbPayloadLoader.releaseDevice()
        finish()
    }
}