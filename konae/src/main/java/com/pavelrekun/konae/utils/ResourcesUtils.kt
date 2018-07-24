package com.pavelrekun.konae.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.webkit.MimeTypeMap

object ResourcesUtils {

    fun dpToPx(dp: Float): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return dp * scale + 0.5f
    }

    fun pxToDp(px: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun resolveFileTypeIcon(context: Context, uri: Uri): Drawable? {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.type = getMimeType(context, uri)

        val pm = context.packageManager
        val matches = pm.queryIntentActivities(intent, 0)

        for (match in matches) {
            return match.loadIcon(pm)
        }

        return null
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        val mimeType: String?

        mimeType = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val contentResolver = context.applicationContext.contentResolver
            contentResolver.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }

        return mimeType
    }


}