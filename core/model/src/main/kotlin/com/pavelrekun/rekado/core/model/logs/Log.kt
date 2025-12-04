package com.pavelrekun.rekado.core.model.logs

data class Log(
    val message: String,
    val level: LogLevel,
    val timestamp: Long = System.currentTimeMillis()
)
