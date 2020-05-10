package com.pavelrekun.rekado.services.utils

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.pavelrekun.penza.services.extensions.EMPTY_STRING
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Config
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper.find

object PreferencesUtils {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.context)

    private const val AUTO_INJECTOR_ENABLED = "auto_injector_enable"
    private const val AUTO_INJECTOR_PAYLOAD = "auto_injector_payload"

    private const val PAYLOADS_HIDE_ENABLED = "payloads_hide"

    private const val CURRENT_CONFIG = "CURRENT_CONFIG"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun checkAutoInjectorEnabled() = sharedPreferences.getBoolean(AUTO_INJECTOR_ENABLED, false)

    fun setHideBundledPayloadsEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(PAYLOADS_HIDE_ENABLED, enabled) }

    fun checkHideBundledPayloadsEnabled() = sharedPreferences.getBoolean(PAYLOADS_HIDE_ENABLED, false)

    fun getAutoInjectorPayload() = sharedPreferences.getString(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BUNDLED_PAYLOADS.first())

    fun saveConfig(config: Config) {
        sharedPreferences.edit {
            val jsonConfig = GsonBuilder().create().toJson(config)
            putString(CURRENT_CONFIG, jsonConfig)
        }
    }

    fun getCurrentConfig(): Config {
        val savedConfig = sharedPreferences.getString(CURRENT_CONFIG, EMPTY_STRING)
        return GsonBuilder().create().fromJson(savedConfig, Config::class.java)
    }

    fun checkConfigExists() = sharedPreferences.contains(CURRENT_CONFIG)

    fun putChosen(payload: Payload) = sharedPreferences.edit { putString(CHOSEN_PAYLOAD, payload.title) }

    fun getChosen(): Payload = find(sharedPreferences.getString(CHOSEN_PAYLOAD, EMPTY_STRING) as String)

}