package com.pavelrekun.rekado.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.pavelrekun.rekado.core.navigation.routes.AboutRekado
import com.pavelrekun.rekado.core.navigation.routes.SerialChecker
import com.pavelrekun.rekado.core.navigation.routes.Settings
import com.pavelrekun.rekado.core.navigation.routes.TopLevelDestination
import com.pavelrekun.rekado.core.ui.components.BottomBarItem
import com.pavelrekun.rekado.core.ui.components.RekadoBottomBar
import com.pavelrekun.rekado.core.ui.components.RekadoTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RekadoApp(
    modifier: Modifier = Modifier,
    appState: RekadoAppState
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.exclude(
                        WindowInsets.ime,
                    ),
                ),
            )
        },
        topBar = {
            RekadoTopBar(
                isOnTopLevelRoute = appState.isOnTopLevelRoute,
                currentRouteTitleId = appState.currentRoute.title,
                goBack = appState.navigator::goBack,
                onSerialCheckerClick = { appState.navigator.navigate(SerialChecker) },
                onDonateClick = {},
                onSettingsClick = { appState.navigator.navigate(Settings) },
                onAboutClick = { appState.navigator.navigate(AboutRekado) }
            )
        },
        bottomBar = {
            if (appState.isOnTopLevelRoute) {
                RekadoBottomBar(
                    items = TopLevelDestination.entries.map { destination ->
                        BottomBarItem(
                            iconId = destination.iconId,
                            titleId = destination.titleId,
                            isSelected = appState.navigationState.topLevelRoute == destination.route,
                            onClick = { appState.navigator.navigate(destination.route) }
                        )
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            RekadoNavDisplay(
                appState = appState
            )
        }
    }
}