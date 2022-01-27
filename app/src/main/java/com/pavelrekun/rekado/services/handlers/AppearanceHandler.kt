package com.pavelrekun.rekado.services.handlers

import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.pavelrekun.rekado.data.base.Theme
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppearanceHandler @Inject constructor(private val edgeToEdgeHandler: EdgeToEdgeHandler,
                                            private val preferencesHandler: PreferencesHandler) {

    fun applyEdgeToEdge(window: Window) {
        edgeToEdgeHandler.applyEdgeToEdgePreference(window)
    }

    fun applySelectedTheme() {
        when (preferencesHandler.checkAppearanceThemeMode()) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun applyDynamicColors(activity: AppCompatActivity) {
        if (checkDynamicColorsAvailable()) {
            val enabled = preferencesHandler.checkAppearanceDynamicColorsEnabled()
            DynamicColors.applyIfAvailable(activity) { _, _ ->
                return@applyIfAvailable enabled
            }
        }
    }

    fun checkDynamicColorsAvailable(): Boolean {
        return DynamicColors.isDynamicColorAvailable()
    }

}