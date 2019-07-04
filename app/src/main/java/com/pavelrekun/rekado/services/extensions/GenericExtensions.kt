package com.pavelrekun.rekado.services.extensions

import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import com.pavelrekun.rekado.RekadoApplication
import java.io.File
import java.io.InputStream

fun EditText.getString() = this.text.toString()

fun EditText.isEmpty() = this.text.isEmpty()

fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}

fun String.extractName(): String {
    return this.substringAfterLast("/")
}

fun Uri.extractFileName(): String? {
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
    RekadoApplication.context.contentResolver.query(this, projection, null, null, null)?.use {
        if (it.moveToFirst()) {
            return it.getString(0)
        }
    }

    return null
}