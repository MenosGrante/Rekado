package com.pavelrekun.rekado.services.utils

import android.support.v7.preference.PreferenceManager
import com.pavelrekun.rang.Rang
import com.pavelrekun.rang.colors.NightMode
import com.pavelrekun.rang.colors.PrimaryColor
import com.pavelrekun.rekado.RekadoApplication

object DesignUtils {

    fun setNightTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.instance.applicationContext)
        val nightMode = preferences.getString("appearance_night_mode", "enabled")

        when (nightMode) {
            "disabled" -> Rang.config(RekadoApplication.instance.applicationContext).nightMode(NightMode.DAY).oledMode(false).apply()
            "enabled" -> Rang.config(RekadoApplication.instance.applicationContext).nightMode(NightMode.NIGHT).oledMode(false).apply()
            "amoled" -> Rang.config(RekadoApplication.instance.applicationContext).primaryColor(PrimaryColor.CASTRO_OLED).nightMode(NightMode.NIGHT).oledMode(true).apply()
        }
    }

}