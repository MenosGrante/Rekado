package com.pavelrekun.rekado.data

import com.google.gson.annotations.SerializedName

data class Config(
        @SerializedName("payloads")
        val payloads: MutableList<Payload>,
        @SerializedName("latestVersionCode")
        val latestVersionCode: Int,
        @SerializedName("timestamp")
        val timestamp: Int
)