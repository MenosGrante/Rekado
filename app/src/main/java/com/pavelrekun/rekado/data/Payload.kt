package com.pavelrekun.rekado.data

import com.google.gson.annotations.SerializedName
import com.pavelrekun.magta.system.EMPTY_STRING
import com.pavelrekun.rekado.services.utils.MemoryUtils.getLocation

data class Payload(
        @SerializedName("title")
        val title: String,
        @SerializedName("version")
        val version: String = EMPTY_STRING,
        @SerializedName("downloadUrl")
        val downloadUrl: String = EMPTY_STRING) {

    fun getPath() = getLocation().absolutePath + "/" + title

}