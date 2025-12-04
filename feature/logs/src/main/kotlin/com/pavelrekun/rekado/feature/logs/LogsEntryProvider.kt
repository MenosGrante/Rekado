package com.pavelrekun.rekado.feature.logs

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.Logs

fun EntryProviderScope<NavKey>.logsEntry(navigator: Navigator) {
    entry<Logs> {
        LogsScreen()
    }
}
