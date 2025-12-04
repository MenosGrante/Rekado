package com.pavelrekun.rekado.feature.settings

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.Settings

fun EntryProviderScope<NavKey>.settingsEntry(navigator: Navigator) {
    entry<Settings> {
        SettingsScreen()
    }
}
