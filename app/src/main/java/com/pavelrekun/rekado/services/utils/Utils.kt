package com.pavelrekun.rekado.services.utils

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.pavelrekun.rekado.RekadoApplication
import kotlin.experimental.and


object Utils {

    private val hexArray = "0123456789ABCDEF".toCharArray()

    fun openLink(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(RekadoApplication.instance.applicationContext, Uri.parse(url))
    }

    // TODO: Compare results to Java equivalent with Lakka
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

    // TODO: Compare results with Java equivalent
    fun readInt32(buffer: ByteArray, offset: Int): Int {
        return ((buffer[offset] and 0xff.toByte()).toInt() or
                ((buffer[offset + 1] and 0xff.toByte()).toInt() shl 8) or
                ((buffer[offset + 2] and 0xff.toByte()).toInt() shl 16) or
                ((buffer[offset + 3] and 0xff.toByte()).toInt() shl 24))
    }

    // TODO: Compare results with Java equivalent
    fun readInt32BE(buffer: ByteArray, offset: Int): Int {
        return ((buffer[offset + 3] and 0xff.toByte()).toInt() or
                (buffer[offset + 2] and 0xff.toByte()).toInt() shl 8 or
                (buffer[offset + 1] and 0xff.toByte()).toInt() shl 16 or
                (buffer[offset] and 0xff.toByte()).toInt() shl 24)
    }

    // TODO: Compare results with Java equivalent
    fun writeInt32(buffer: ByteArray, offset: Int, i: Int) {
        buffer[offset] = (i and 0xff).toByte()
        buffer[offset + 1] = (i shr 8 and 0xff).toByte()
        buffer[offset + 2] = (i shr 16 and 0xff).toByte()
        buffer[offset + 3] = (i shr 24 and 0xff).toByte()
    }

}