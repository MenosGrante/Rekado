package com.pavelrekun.rekado.feature.settings.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AboutScreen(
    text: String = "About Rekado"
) {
    AboutContent(
        text = text
    )
}

@Composable
private fun AboutContent(
    text: String
) {
    Text("$text")
}
