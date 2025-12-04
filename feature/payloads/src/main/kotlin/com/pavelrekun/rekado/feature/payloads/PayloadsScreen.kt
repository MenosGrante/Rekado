package com.pavelrekun.rekado.feature.payloads

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PayloadsScreen(
    text: String = "Payloads Screen"
) {
    PayloadsContent(
        text = text
    )
}

@Composable
private fun PayloadsContent(
    text: String
) {
    Text("$text")
}
