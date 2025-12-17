package com.pavelrekun.rekado.core.ui.composition

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("SnackbarHostState state should be initialized at runtime")
}
