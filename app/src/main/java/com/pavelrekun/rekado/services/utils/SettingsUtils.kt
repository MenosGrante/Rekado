package com.pavelrekun.rekado.services.utils

import androidx.preference.PreferenceManager
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.payloads.PayloadHelper

object SettingsUtils {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.context)

    private const val AUTO_INJECTOR_ENABLED = "auto_injector_enable"
    private const val AUTO_INJECTOR_PAYLOAD = "auto_injector_payload"

    private const val HIDE_BUNDLED_ENABLED = "payloads_hide_bundled"

    fun checkAutoInjectorEnabled() = sharedPreferences.getBoolean(AUTO_INJECTOR_ENABLED, false)

    fun getAutoInjectorPayload() = sharedPreferences.getString(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BUNDLED_PAYLOAD_HEKATE)

    fun checkHideBundledEnabled() = sharedPreferences.getBoolean(HIDE_BUNDLED_ENABLED, false)

}