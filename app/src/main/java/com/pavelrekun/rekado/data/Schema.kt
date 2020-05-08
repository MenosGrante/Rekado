package com.pavelrekun.rekado.data

import com.google.gson.annotations.SerializedName

data class Schema(
        @SerializedName("payloads")
        val payloads: MutableList<Payload>,
        @SerializedName("timestamp")
        val timestamp: Int
)