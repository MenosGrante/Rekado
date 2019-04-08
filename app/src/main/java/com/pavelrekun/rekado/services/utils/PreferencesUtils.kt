package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.siga.data.Theme
import com.pavelrekun.siga.services.Siga

object PreferencesUtils {

    fun getPreferencesTheme() {
        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(RekadoApplication.instance.applicationContext)
        val theme = preferences.getString("appearance_interface_theme", Theme.DARK_DEFAULT.id)

        Siga.config(RekadoApplication.instance.applicationContext).theme(Theme.findById(theme)).apply()
    }
}