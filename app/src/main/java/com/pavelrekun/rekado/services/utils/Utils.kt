package com.pavelrekun.rekado.services.utils

import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.net.ConnectivityManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.base.BaseActivity


object Utils {

    private const val RCM_DEVICE_ID = 0x0955
    private const val RCM_VENDOR_ID = 0x7321

    private val hexArray = "0123456789ABCDEF".toCharArray()

    fun openLink(activity: BaseActivity, url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))
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

    fun restartApplication() {
        val context = RekadoApplication.context
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName) as Intent
        val mainIntent = Intent.makeRestartActivityTask(intent.component)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager = RekadoApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun isRCM(device: UsbDevice) = device.vendorId == RCM_DEVICE_ID && device.productId == RCM_VENDOR_ID

}