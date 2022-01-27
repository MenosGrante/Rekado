package com.pavelrekun.rekado.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
        val payloads: MutableList<Payload>,
        val revision: Int
)