package com.pavelrekun.rekado.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Payload(
        val title: String,
        val version: String?,
        val downloadUrl: String?)