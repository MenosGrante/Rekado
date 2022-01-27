package com.pavelrekun.rekado.services.utils

import android.content.Context
import android.hardware.usb.UsbDevice
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object Utils {

    private const val RCM_DEVICE_ID = 0x0955
    private const val RCM_VENDOR_ID = 0x7321

    private val hexArray = "0123456789ABCDEF".toCharArray()

    fun openLink(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    fun bytesToHex(bytes: ByteArray): String {
        val result = StringBuffer()

        bytes.forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(hexArray[firstIndex])
            result.append(hexArray[secondIndex])
        }

        return result.toString()
    }

    fun isRCM(device: UsbDevice) = device.vendorId == RCM_DEVICE_ID && device.productId == RCM_VENDOR_ID

}