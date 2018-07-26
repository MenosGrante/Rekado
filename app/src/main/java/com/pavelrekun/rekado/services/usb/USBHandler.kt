package com.pavelrekun.rekado.services.usb

import android.hardware.usb.UsbDevice

interface USBHandler {

    fun handleDevice(device: UsbDevice)

    fun releaseDevice()

}