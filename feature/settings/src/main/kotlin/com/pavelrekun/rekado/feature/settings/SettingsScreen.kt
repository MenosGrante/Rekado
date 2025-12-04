package com.pavelrekun.rekado.feature.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(
    text: String = "Settings Screen"
) {
    SettingsContent(
        text = text
    )
}

@Composable
private fun SettingsContent(
    text: String
) {
    Text("$text")
}
