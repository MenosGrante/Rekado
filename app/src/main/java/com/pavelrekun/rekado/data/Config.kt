package com.pavelrekun.rekado.data

import kotlinx.serialization.Serializable

@Serializable
data class Config(
        val payloads: MutableList<Payload>,
        val revision: Int
)