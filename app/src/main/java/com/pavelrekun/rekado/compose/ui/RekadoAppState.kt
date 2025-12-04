package com.pavelrekun.rekado.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.NavigationState
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.rememberNavigationState
import com.pavelrekun.rekado.core.navigation.routes.NavigationRoute
import com.pavelrekun.rekado.core.navigation.routes.Payloads
import com.pavelrekun.rekado.core.navigation.routes.TopLevelDestination

@Composable
fun rememberAppState(): RekadoAppState {
    val topLevelRoutes = TopLevelDestination.entries.map { it.route }.toSet()

    val navigationState = rememberNavigationState(
        startRoute = Payloads,
        topLevelRoutes = topLevelRoutes
    )

    val navigator = remember(navigationState) { Navigator(navigationState) }

    return remember(
        navigationState,
        navigator
    ) {
        RekadoAppState(
            navigationState = navigationState,
            navigator = navigator
        )
    }
}

@Stable
class RekadoAppState(
    val navigationState: NavigationState,
    val navigator: Navigator
) {
    val currentBackStack: NavBackStack<NavKey>?
        get() = navigationState.backStacks[navigationState.topLevelRoute]

    val isOnTopLevelRoute: Boolean
        get() = currentBackStack?.size == 1

    val currentRoute: NavigationRoute
        get() = (currentBackStack?.lastOrNull() ?: navigationState.topLevelRoute) as NavigationRoute
}