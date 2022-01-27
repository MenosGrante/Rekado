package com.pavelrekun.rekado.services.handlers

import android.content.SharedPreferences
import androidx.core.content.edit
import com.pavelrekun.rekado.data.Config
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.data.base.Theme
import com.pavelrekun.rekado.services.extensions.toConfig
import com.pavelrekun.rekado.services.extensions.toJson

class PreferencesHandler(private val sharedPreferences: SharedPreferences) {

    fun checkAppearanceThemeMode(): Theme {
        val themeMode = sharedPreferences.getString(APPEARANCE_THEME, Theme.SYSTEM_DEFAULT.name)
                ?: Theme.SYSTEM_DEFAULT.name
        return Theme.valueOf(themeMode)
    }

    fun saveAppearanceThemeMode(theme: Theme) = sharedPreferences.edit { putString(APPEARANCE_THEME, theme.name) }

    fun checkAppearanceDynamicColorsEnabled() = sharedPreferences.getBoolean(APPEARANCE_DYNAMIC_COLORS, true)

    fun checkAutoInjectorEnabled() = sharedPreferences.getBoolean(AUTO_INJECTOR_ENABLED, false)

    fun setHideBundledPayloadsEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(PAYLOADS_HIDE_ENABLED, enabled) }

    fun checkHideBundledPayloadsEnabled() = sharedPreferences.getBoolean(PAYLOADS_HIDE_ENABLED, false)

    fun getAutoInjectorPayload(defaultPayloadTitle: String): String {
        return sharedPreferences.getString(AUTO_INJECTOR_PAYLOAD, defaultPayloadTitle) ?: defaultPayloadTitle
    }

    fun saveAutoInjectorPayload(payloadTitle: String) {
        sharedPreferences.edit { putString(AUTO_INJECTOR_PAYLOAD, payloadTitle) }
    }

    fun clearAutoInjectorPayload() = sharedPreferences.edit { remove(AUTO_INJECTOR_PAYLOAD) }

    fun saveConfig(config: Config) {
        sharedPreferences.edit {
            putString(CURRENT_CONFIG, config.toJson())
        }
    }

    fun getCurrentConfig(): Config {
        val savedConfig = sharedPreferences.getString(CURRENT_CONFIG, "")
        return savedConfig?.toConfig() as Config
    }

    fun checkConfigExists() = sharedPreferences.contains(CURRENT_CONFIG)

    companion object {

        private const val APPEARANCE_THEME = "appearance_theme"
        private const val APPEARANCE_DYNAMIC_COLORS = "appearance_dynamic_colors"

        private const val AUTO_INJECTOR_ENABLED = "auto_injector_enable"
        private const val AUTO_INJECTOR_PAYLOAD = "auto_injector_payload"

        private const val PAYLOADS_HIDE_ENABLED = "payloads_hide"

        private const val CURRENT_CONFIG = "CURRENT_CONFIG"

    }

}