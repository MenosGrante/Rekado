package com.pavelrekun.rekado.services.utils

import android.support.v7.preference.PreferenceManager
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import io.paperdb.Paper

object SettingsUtils {

    private const val AUTO_INJECTOR_ENABLED = "AUTO_INJECTOR_ENABLED"
    private const val AUTO_INJECTOR_PAYLOAD = "AUTO_INJECTOR_PAYLOAD"
    private const val COREBOOT_UPDATE_DATE = "COREBOOT_UPDATE_DATE"

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.instance.applicationContext)


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
        return Paper.book().read(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BASIC_PAYLOAD_NAME)
    }

    fun saveCorebootUpdateDate() {
        sharedPreferences.edit().putString(COREBOOT_UPDATE_DATE, Utils.getCurrentDate()).apply()
    }

    fun getCorebootUpdateDate(): String? {
        return sharedPreferences.getString(COREBOOT_UPDATE_DATE, null)
    }

    fun checkCorebootUpdateDate(): Boolean {
        return getCorebootUpdateDate() != null
    }

}