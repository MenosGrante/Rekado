package com.pavelrekun.rekado.compose

import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig.DARK
import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig.FOLLOW_SYSTEM
import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig.LIGHT
import com.pavelrekun.rekado.core.model.userdata.UserData

sealed interface NavigationUiState {
    data object Loading : NavigationUiState

    data class Success(val userData: UserData) : NavigationUiState {
        override val shouldDisableDynamicTheming = !userData.useDynamicColor

        override fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) =
            when (userData.darkThemeConfig) {
                FOLLOW_SYSTEM -> isSystemDarkTheme
                LIGHT -> false
                DARK -> true
            }
    }

    /**
     * Returns `true` if the state wasn't loaded yet and it should keep showing the splash screen.
     */
    fun shouldKeepSplashScreen() = this is Loading

    /**
     * Returns `true` if the dynamic color is disabled.
     */
    val shouldDisableDynamicTheming: Boolean get() = true

    /**
     * Returns `true` if the Android theme should be used.
     */
    val shouldUseAndroidTheme: Boolean get() = false

    /**
     * Returns `true` if dark theme should be used.
     */
    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
}