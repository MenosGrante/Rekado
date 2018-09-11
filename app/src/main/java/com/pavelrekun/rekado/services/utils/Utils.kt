package com.pavelrekun.rekado.services.utils

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.pavelrekun.rekado.RekadoApplication
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    private val hexArray = "0123456789ABCDEF".toCharArray()

    fun openLink(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(RekadoApplication.instance.applicationContext, Uri.parse(url))
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