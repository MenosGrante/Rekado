package com.pavelrekun.rekado.feature.serialchecker

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.SerialChecker

fun EntryProviderScope<NavKey>.serialCheckerEntry(navigator: Navigator) {
    entry<SerialChecker> {
        SerialCheckerScreen()
    }
}
