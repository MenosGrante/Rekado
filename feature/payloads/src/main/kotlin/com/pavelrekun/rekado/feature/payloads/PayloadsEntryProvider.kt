package com.pavelrekun.rekado.feature.payloads

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.Payloads

fun EntryProviderScope<NavKey>.payloadsEntry(navigator: Navigator) {
    entry<Payloads> {
        PayloadsScreen()
    }
}
