package com.pavelrekun.rekado.services.extensions

import android.widget.EditText

fun EditText.getString(upperCase: Boolean = false): String {
    return if (upperCase) {
        this.text.toString().toUpperCase()
    } else {
        this.text.toString()
    }
}

fun EditText.isEmpty() = this.text.isEmpty()

fun EditText.length() = this.textSize.toString().length