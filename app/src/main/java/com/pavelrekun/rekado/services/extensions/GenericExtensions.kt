package com.pavelrekun.rekado.services.extensions

import android.widget.EditText
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