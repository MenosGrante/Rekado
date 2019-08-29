package com.pavelrekun.rekado.services.utils

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.main_activity.MainActivity

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

    fun restartApplication(activity: BaseActivity) {
        val intent = Intent(activity, MainActivity::class.java)
        ActivityCompat.finishAffinity(activity)
        activity.startActivity(intent)
    }
}