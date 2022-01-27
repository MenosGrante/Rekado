package com.pavelrekun.rekado.services.extensions

import android.widget.EditText

/**
 * Get string from the given [EditText].
 *
 * @return - currently entered text.
 */
fun EditText.getString() = this.text.toString()

/**
 * Checks if text, entered in the [EditText] is empty.
 *
 * @return - true, if [EditText] has no entered text, false otherwise.
 */
fun EditText.isEmpty() = this.text.isEmpty()