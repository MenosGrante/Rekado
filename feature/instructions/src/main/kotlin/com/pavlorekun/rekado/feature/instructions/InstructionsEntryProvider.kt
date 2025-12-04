package com.pavlorekun.rekado.feature.instructions

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.navigation.routes.Instructions

fun EntryProviderScope<NavKey>.instructionsEntry(navigator: Navigator) {
    entry<Instructions> {
        InstructionsScreen(navigator = navigator)
    }
}
