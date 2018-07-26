package com.pavelrekun.rekado.services.utils

import android.hardware.usb.UsbDevice

object SwitchUtils {

    fun isRCM(device: UsbDevice): Boolean {
        return device.vendorId == 0x0955 && device.productId == 0x7321
    }

    fun isUBoot(device: UsbDevice): Boolean {
        return device.vendorId == 0x0955 && device.productId == 0x701a
    }

    fun isCompatible(device: UsbDevice): Boolean {
        return isRCM(device) || isUBoot(device)
    }

}