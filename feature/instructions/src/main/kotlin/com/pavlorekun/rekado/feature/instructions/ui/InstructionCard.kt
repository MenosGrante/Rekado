package com.pavlorekun.rekado.feature.instructions.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import com.pavlorekun.rekado.feature.instructions.data.InstructionItem

private const val CARD_CORNER_RADIUS = 28
private const val CARD_PADDING = 16
private const val CARD_VERTICAL_GAP = 16
private const val ICON_BUTTON_SIZE = 48
private const val ICON_SIZE = 24

@Composable
fun InstructionCard(
    modifier: Modifier = Modifier,
    instruction: InstructionItem,
    onRcmHelpClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(CARD_CORNER_RADIUS.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CARD_PADDING.dp),
            verticalArrangement = Arrangement.spacedBy(CARD_VERTICAL_GAP.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(instruction.titleId),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (instruction.isRcmInstruction) {
                    Box(
                        modifier = Modifier
                            .size(ICON_BUTTON_SIZE.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            )
                            .clickable { onRcmHelpClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(ICON_SIZE.dp),
                            painter = painterResource(R.drawable.ic_navigation_help),
                            contentDescription = stringResource(R.string.instructions_content_description_help),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Text(
                text = stringResource(instruction.descriptionId),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.3f,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(name = "Instruction Card - Normal")
@Composable
private fun InstructionCardNormalPreview() = RekadoTheme {
    InstructionCard(
        instruction = InstructionItem(
            titleId = R.string.instructions_category_cable,
            descriptionId = R.string.instructions_category_cable_description
        ),
        onRcmHelpClick = {}
    )
}

@Preview(name = "Instruction Card - RCM (with help)")
@Composable
private fun InstructionCardRcmPreview() = RekadoTheme {
    InstructionCard(
        instruction = InstructionItem(
            titleId = R.string.instructions_category_rcm,
            descriptionId = R.string.instructions_category_rcm_description,
            isRcmInstruction = true
        ),
        onRcmHelpClick = {}
    )
}
