package com.pavelrekun.rekado.feature.serialchecker

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SerialCheckerScreen(
    text: String = "Serial Checker Screen"
) {
    SerialCheckerContent(
        text = text
    )
}

@Composable
private fun SerialCheckerContent(
    text: String
) {
    Text("$text")
}
