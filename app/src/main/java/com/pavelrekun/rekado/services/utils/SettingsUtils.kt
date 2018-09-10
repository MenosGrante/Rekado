package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.services.payloads.PayloadHelper
import io.paperdb.Paper

object SettingsUtils {

    private const val AUTO_INJECTOR_ENABLED = "AUTO_INJECTOR_ENABLED"
    private const val AUTO_INJECTOR_PAYLOAD = "AUTO_INJECTOR_PAYLOAD"

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
        return Paper.book().read(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BUNDLED_PAYLOAD_SX)
    }

}