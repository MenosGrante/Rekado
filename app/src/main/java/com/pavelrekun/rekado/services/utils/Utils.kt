package com.pavelrekun.rekado.services.utils

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.pavelrekun.rekado.base.BaseActivity


object Utils {

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
}