package com.pavelrekun.rekado.feature.serialchecker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.Empty
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.NotPatched
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.Patched
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.PossiblyPatched
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.UnknownPrefix

private const val MAX_SERIAL_LENGTH = 14

@Composable
fun CheckerCard(
    modifier: Modifier = Modifier,
    serialNumber: String,
    status: SerialStatus,
    onSerialNumberChanged: (String) -> Unit,
    onScanBarcode: () -> Unit
) {
    val statusLabel = when (status) {
        Empty -> R.string.serial_checker_status_label_empty
        NotPatched -> R.string.serial_checker_status_label_not_patched
        PossiblyPatched -> R.string.serial_checker_status_label_possibly_patched
        Patched -> R.string.serial_checker_status_label_patched
        UnknownPrefix -> R.string.serial_checker_status_label_unknown
    }

    val statusDescription = when (status) {
        Empty -> R.string.serial_checker_status_description_empty
        NotPatched -> R.string.serial_checker_status_description_not_patched
        PossiblyPatched -> R.string.serial_checker_status_description_possibly_patched
        Patched -> R.string.serial_checker_status_description_patched
        UnknownPrefix -> R.string.serial_checker_status_description_unknown
    }

    val statusBackground = when (status) {
        NotPatched -> R.color.serial_checker_status_not_patched
        Patched -> R.color.serial_checker_status_patched
        PossiblyPatched -> R.color.serial_checker_status_possibly_patched
        else -> null
    }

    val isActiveStatus = status != Empty && status != UnknownPrefix

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.serial_checker_category_checker),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .clickable { onScanBarcode() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigation_serial_checker),
                        contentDescription = stringResource(R.string.serial_checker_content_description_scan),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = serialNumber,
                onValueChange = { newValue ->
                    if (newValue.length <= MAX_SERIAL_LENGTH) {
                        onSerialNumberChanged(newValue)
                    }
                },
                label = { Text(stringResource(R.string.serial_checker_checker_serial_number)) },
                supportingText = { Text("${serialNumber.length}/$MAX_SERIAL_LENGTH") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        statusBackground?.let { colorResource(it) } ?: MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(statusLabel),
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isActiveStatus) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = stringResource(statusDescription),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isActiveStatus) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(name = "Checker Card - Empty")
@Composable
private fun CheckerCardEmptyPreview() = RekadoTheme {
    CheckerCard(
        serialNumber = "",
        status = Empty,
        onSerialNumberChanged = {},
        onScanBarcode = {}
    )
}

@Preview(name = "Checker Card - Not Patched")
@Composable
private fun CheckerCardNotPatchedPreview() = RekadoTheme {
    CheckerCard(
        serialNumber = "XAW10050000000",
        status = NotPatched,
        onSerialNumberChanged = {},
        onScanBarcode = {}
    )
}

@Preview(name = "Checker Card - Patched")
@Composable
private fun CheckerCardPatchedPreview() = RekadoTheme {
    CheckerCard(
        serialNumber = "XKW10000000000",
        status = Patched,
        onSerialNumberChanged = {},
        onScanBarcode = {}
    )
}
