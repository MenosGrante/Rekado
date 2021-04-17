package com.pavelrekun.rekado.services.extensions

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.pavelrekun.rekado.data.Config
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

private val CONFIG_TYPE = object : TypeToken<Config>() {}.type

fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}

fun InputStream.parseConfig(): Config = GsonBuilder().create().fromJson(JsonReader(InputStreamReader(this)), CONFIG_TYPE)