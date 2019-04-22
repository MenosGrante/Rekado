package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.services.payloads.PayloadHelper
import io.paperdb.Paper

object SettingsUtils {

    private const val AUTO_INJECTOR_ENABLED = "AUTO_INJECTOR_ENABLED"
    private const val AUTO_INJECTOR_PAYLOAD = "AUTO_INJECTOR_PAYLOAD"

    private const val HIDE_BUNDLED_ENABLED = "HIDE_BUNDLED_ENABLED"

    fun updateAutoInjectorEnabled(enabled: Boolean) {
        Paper.book().write(AUTO_INJECTOR_ENABLED, enabled)
    }

    fun checkAutoInjectorEnabled(): Boolean {
        return Paper.book().read(AUTO_INJECTOR_ENABLED, false)
    }

    fun updateAutoInjectorPayload(payloadName: String) {
        Paper.book().write(AUTO_INJECTOR_PAYLOAD, payloadName)
    }

    fun getAutoInjectorPayload(): String {
        return Paper.book().read(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BUNDLED_PAYLOAD_HEKATE)
    }

    fun updateHideBundledEnabled(enabled: Boolean) {
        Paper.book().write(HIDE_BUNDLED_ENABLED, enabled)
    }

    fun checkHideBundledEnabled(): Boolean  {
        return Paper.book().read(HIDE_BUNDLED_ENABLED, false)
    }

}