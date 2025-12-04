package com.pavelrekun.rekado.core.navigation.routes

import androidx.annotation.DrawableRes
import com.pavelrekun.rekado.core.ui.R

enum class TopLevelDestination(
    @param:DrawableRes val iconId: Int,
    @param:DrawableRes val titleId: Int,
    val route: NavigationRoute
) {
    PAYLOADS(
        iconId = R.drawable.ic_navigation_payloads,
        titleId = R.string.navigation_payloads,
        route = Payloads
    ),
    INSTRUCTIONS(
        iconId = R.drawable.ic_navigation_instructions,
        titleId = R.string.navigation_instructions,
        route = Instructions
    ),
    LOGS(
        iconId = R.drawable.ic_navigation_logs,
        titleId = R.string.navigation_logs,
        route = Logs
    )
}
