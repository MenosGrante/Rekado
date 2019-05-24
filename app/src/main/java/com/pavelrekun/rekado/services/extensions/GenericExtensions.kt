package com.pavelrekun.rekado.services.extensions

import android.widget.EditText
import androidx.core.text.HtmlCompat

fun EditText.getString() = this.text.toString()

fun String.fromHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)