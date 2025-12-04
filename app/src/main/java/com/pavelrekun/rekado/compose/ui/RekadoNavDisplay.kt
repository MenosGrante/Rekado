package com.pavelrekun.rekado.compose.ui

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.pavelrekun.rekado.core.navigation.toEntries
import com.pavelrekun.rekado.feature.logs.logsEntry
import com.pavelrekun.rekado.feature.payloads.payloadsEntry
import com.pavelrekun.rekado.feature.serialchecker.serialCheckerEntry
import com.pavelrekun.rekado.feature.settings.about.aboutEntry
import com.pavelrekun.rekado.feature.settings.settingsEntry
import com.pavlorekun.rekado.feature.instructions.instructionsEntry

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun RekadoNavDisplay(
    modifier: Modifier = Modifier,
    appState: RekadoAppState
) {
    val motionScheme = MaterialTheme.motionScheme

    val entries = appState.navigationState.toEntries(
        entryProvider = entryProvider {
            with(appState.navigator) {
                payloadsEntry(this)
                instructionsEntry(this)
                logsEntry(this)
                serialCheckerEntry(this)
                settingsEntry(this)
                aboutEntry(this)
            }
        }
    )

    NavDisplay(
        modifier = modifier,
        entries = entries,
        transitionSpec = {
            ContentTransform(
                fadeIn(motionScheme.defaultEffectsSpec()),
                fadeOut(motionScheme.defaultEffectsSpec()),
            )
        },
        popTransitionSpec = {
            ContentTransform(
                fadeIn(motionScheme.defaultEffectsSpec()),
                fadeOut(motionScheme.defaultEffectsSpec())
            )
        },
        onBack = { appState.navigator.goBack() }
    )
}

