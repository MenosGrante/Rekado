package com.pavelrekun.rekado.feature.serialchecker.data

sealed interface SerialStatus {
    data object Empty : SerialStatus
    data object NotPatched : SerialStatus
    data object Patched : SerialStatus
    data object PossiblyPatched : SerialStatus
    data object UnknownPrefix : SerialStatus
}