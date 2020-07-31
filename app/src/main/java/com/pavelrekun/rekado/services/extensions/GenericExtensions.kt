package com.pavelrekun.rekado.services.extensions

import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Config
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

private val CONFIG_TYPE = object : TypeToken<Config>() {}.type

fun EditText.getString() = this.text.toString()

fun EditText.isEmpty() = this.text.isEmpty()

fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
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

fun InputStream.parseConfig(): Config = GsonBuilder().create().fromJson(JsonReader(InputStreamReader(this)), CONFIG_TYPE)