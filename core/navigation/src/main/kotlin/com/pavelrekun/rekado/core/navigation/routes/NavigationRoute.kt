package com.pavelrekun.rekado.core.navigation.routes

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.ui.R
import kotlinx.serialization.Serializable

sealed interface NavigationRoute : NavKey {
    @get:StringRes
    val title: Int
}

@Serializable
data object Instructions : NavigationRoute {
    override val title: Int = R.string.navigation_instructions
}

@Serializable
data object Payloads : NavigationRoute {
    override val title: Int = R.string.navigation_payloads
}

@Serializable
data object SerialChecker : NavigationRoute {
    override val title: Int = R.string.navigation_serial_checker
}

@Serializable
data object Logs : NavigationRoute {
    override val title: Int = R.string.navigation_logs
}

@Serializable
data object Settings : NavigationRoute {
    override val title: Int = R.string.navigation_settings
}

@Serializable
data object AboutRekado : NavigationRoute {
    override val title: Int = R.string.navigation_about
}