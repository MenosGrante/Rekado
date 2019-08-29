package com.pavelrekun.rekado.services

import com.pavelrekun.rekado.data.Log

object Logger {

    private val logs = mutableListOf<Log>()

    private const val INFO = 1
    private const val ERROR = 0

    fun init(isCleared: Boolean) {
        if (isCleared) {
            info("Logs cleared!")
        } else {
            info("Application started!")
        }
    }

    fun info(message: String) = logs.add(Log(message, INFO))

    fun error(message: String) = logs.add(Log(message, ERROR))

    fun getLogs() = logs

    fun clearLogs() {
        logs.clear()

        init(true)
    }
}