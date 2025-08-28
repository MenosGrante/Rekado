package com.pavelrekun.rekado.data

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
        val title: String,
        val version: String?,
        val downloadUrl: String?)