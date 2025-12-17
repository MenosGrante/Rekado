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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.NotPatched
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.Patched
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus.PossiblyPatched

@Composable
fun InformationCard(
    modifier: Modifier = Modifier,
    onHelpClick: () -> Unit
) {
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
                    text = stringResource(R.string.serial_checker_category_information),
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
                        .clickable { onHelpClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigation_help),
                        contentDescription = stringResource(R.string.serial_checker_content_description_help),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = stringResource(R.string.serial_checker_information_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            StatusLegend()

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SerialRangeList()
            }
        }
    }
}

@Composable
private fun StatusLegend(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LegendItem(
            color = colorResource(R.color.serial_checker_status_not_patched),
            label = stringResource(R.string.serial_checker_legend_not_patched),
            description = stringResource(R.string.serial_checker_legend_not_patched_description)
        )
        LegendItem(
            color = colorResource(R.color.serial_checker_status_possibly_patched),
            label = stringResource(R.string.serial_checker_legend_possibly_patched),
            description = stringResource(R.string.serial_checker_legend_possibly_patched_description)
        )
        LegendItem(
            color = colorResource(R.color.serial_checker_status_patched),
            label = stringResource(R.string.serial_checker_legend_patched),
            description = stringResource(R.string.serial_checker_legend_patched_description)
        )
    }
}

@Composable
private fun LegendItem(
    modifier: Modifier = Modifier,
    color: Color,
    label: String,
    description: String
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SerialRangeList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SerialRangeCard(
            prefix = "XAW1",
            notPatchedRange = "00000000 - 10074000000",
            possiblyPatchedRange = "10074000000 - 10120000000",
            patchedRange = "10120000000+"
        )
        SerialRangeCard(
            prefix = "XAW4",
            notPatchedRange = "00000000 - 40011000000",
            possiblyPatchedRange = "40011000000 - 40012000000",
            patchedRange = "40012000000+"
        )
        SerialRangeCard(
            prefix = "XAW7",
            notPatchedRange = "00000000 - 70017800000",
            possiblyPatchedRange = "70017800000 - 70030000000",
            patchedRange = "70030000000+"
        )
        SingleStatusCard(
            prefix = "XAW9",
            status = PossiblyPatched,
            description = stringResource(R.string.serial_checker_range_xaw9_description)
        )
        SerialRangeCard(
            prefix = "XAJ1",
            notPatchedRange = "00000000 - 10020000000",
            possiblyPatchedRange = "10020000000 - 10030000000",
            patchedRange = "10030000000+"
        )
        SerialRangeCard(
            prefix = "XAJ4",
            notPatchedRange = "00000000 - 40046000000",
            possiblyPatchedRange = "40046000000 - 40060000000",
            patchedRange = "40060000000+"
        )
        SerialRangeCard(
            prefix = "XAJ7",
            notPatchedRange = "00000000 - 70040000000",
            possiblyPatchedRange = "70040000000 - 70050000000",
            patchedRange = "70050000000+"
        )
        SingleStatusCard(
            prefix = "XAK",
            status = PossiblyPatched,
            description = stringResource(R.string.serial_checker_range_xak_description)
        )

        SingleStatusCard(
            prefix = "XKW",
            status = Patched,
            description = stringResource(R.string.serial_checker_range_new_description)
        )
        SingleStatusCard(
            prefix = "XKJ",
            status = Patched,
            description = stringResource(R.string.serial_checker_range_new_description)
        )
        SingleStatusCard(
            prefix = "XJW",
            status = Patched,
            description = stringResource(R.string.serial_checker_range_new_description)
        )
        SingleStatusCard(
            prefix = "XWW",
            status = Patched,
            description = stringResource(R.string.serial_checker_range_new_description)
        )
    }
}

@Composable
private fun SerialRangeCard(
    modifier: Modifier = Modifier,
    prefix: String,
    notPatchedRange: String,
    possiblyPatchedRange: String,
    patchedRange: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = prefix,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        RangeItem(
            color = colorResource(R.color.serial_checker_status_not_patched),
            label = stringResource(R.string.serial_checker_range_hackable),
            range = notPatchedRange
        )

        RangeItem(
            color = colorResource(R.color.serial_checker_status_possibly_patched),
            label = stringResource(R.string.serial_checker_range_uncertain),
            range = possiblyPatchedRange
        )

        RangeItem(
            color = colorResource(R.color.serial_checker_status_patched),
            label = stringResource(R.string.serial_checker_range_patched),
            range = patchedRange
        )
    }
}

@Composable
private fun RangeItem(
    modifier: Modifier = Modifier,
    color: Color,
    label: String,
    range: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = color,
            modifier = Modifier.width(70.dp)
        )

        Text(
            text = range,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SingleStatusCard(
    modifier: Modifier = Modifier,
    prefix: String,
    status: SerialStatus,
    description: String
) {
    val statusColor = when (status) {
        NotPatched -> colorResource(R.color.serial_checker_status_not_patched)
        PossiblyPatched -> colorResource(R.color.serial_checker_status_possibly_patched)
        Patched -> colorResource(R.color.serial_checker_status_patched)
        else -> MaterialTheme.colorScheme.outline
    }

    val statusLabel = when (status) {
        NotPatched -> stringResource(R.string.serial_checker_range_hackable)
        PossiblyPatched -> stringResource(R.string.serial_checker_range_uncertain)
        Patched -> stringResource(R.string.serial_checker_range_patched)
        else -> ""
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = prefix,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )

            Text(
                text = statusLabel,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = statusColor,
                modifier = Modifier.width(70.dp)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Information Card")
@Composable
private fun InformationCardPreview() = RekadoTheme {
    InformationCard(
        onHelpClick = {}
    )
}
