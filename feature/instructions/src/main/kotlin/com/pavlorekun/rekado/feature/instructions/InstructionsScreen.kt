package com.pavlorekun.rekado.feature.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.common.Links.HELP_RCM
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import com.pavlorekun.rekado.feature.instructions.data.InstructionItem
import com.pavlorekun.rekado.feature.instructions.data.instructions
import com.pavlorekun.rekado.feature.instructions.ui.InstructionCard
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
    modifier: Modifier = Modifier,
    instructions: PersistentList<InstructionItem>,
    onRcmHelpClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
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

@Preview(name = "Instructions Screen")
@Composable
private fun InstructionsContentPreview() = RekadoTheme {
    InstructionsContent(
        instructions = instructions,
        onRcmHelpClick = {}
    )
}
