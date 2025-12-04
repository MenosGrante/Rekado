package com.pavelrekun.rekado.feature.settings.about

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.AboutRekado

fun EntryProviderScope<NavKey>.aboutEntry(navigator: Navigator) {
    entry<AboutRekado> {
        AboutScreen()
    }
}
