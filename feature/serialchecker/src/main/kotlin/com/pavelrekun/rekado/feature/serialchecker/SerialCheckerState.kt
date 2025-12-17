package com.pavelrekun.rekado.feature.serialchecker

import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus

data class SerialCheckerState(
    val serialNumber: String = "",
    val status: SerialStatus = SerialStatus.Empty
)