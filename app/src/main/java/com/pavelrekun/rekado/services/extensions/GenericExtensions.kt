package com.pavelrekun.rekado.services.extensions

import android.widget.EditText
import androidx.core.text.HtmlCompat
import com.pavelrekun.rekado.RekadoApplication

fun EditText.getString() = this.text.toString()

fun EditText.isEmpty() = this.text.isEmpty()