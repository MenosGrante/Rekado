package com.pavlorekun.rekado.feature.instructions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.common.Links.HELP_RCM
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import kotlinx.collections.immutable.PersistentList

@Composable
fun InstructionsScreen(
    navigator: Navigator
) {
    val context = LocalContext.current

    InstructionsContent(
        instructions = instructions,
        onRcmHelpClick = {
            navigator.openLink(
                context = context,
                url = HELP_RCM
            )
        }
    )
}

@Composable
private fun InstructionsContent(
    instructions: PersistentList<InstructionItem>,
    onRcmHelpClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = instructions,
            key = { it.titleId }
        ) { instruction ->
            InstructionCard(
                instruction = instruction,
                onRcmHelpClick = onRcmHelpClick
            )
        }
    }
}

@Composable
private fun InstructionCard(
    instruction: InstructionItem,
    onRcmHelpClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(instruction.titleId),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (instruction.isRcmInstruction) {
                    Icon(
                        modifier = Modifier.clickable { onRcmHelpClick() },
                        painter = painterResource(R.drawable.ic_instructions_help),
                        contentDescription = stringResource(R.string.instructions_content_description_help)
                    )

//                    IconButton(onClick = onRcmHelpClick) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_instructions_help),
//                            contentDescription = stringResource(R.string.instructions_content_description_help)
//                        )
//                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(instruction.descriptionId),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.3f,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(name = "Instructions Screen")
@Composable
private fun InstructionsContentPreview() = RekadoTheme {
    InstructionsContent(
        instructions = instructions,
        onRcmHelpClick = {}
    )
}
