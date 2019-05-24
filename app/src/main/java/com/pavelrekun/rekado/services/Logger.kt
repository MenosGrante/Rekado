package com.pavelrekun.rekado.services

import com.pavelrekun.rekado.data.Log
import io.paperdb.Paper

object Logger {

    private const val LOGS_LIST_KEY = "LOGS_LIST_KEY"

    private const val INFO = 1
    private const val ERROR = 0

    private lateinit var logsList: MutableList<Log>

    fun init() {
        logsList = ArrayList()
        info("Application started!")
        saveLogs()
    }

    fun info(message: String) {
        logsList.add(Log(message, INFO))
        saveLogs()
    }

    fun error(message: String) {
        logsList.add(Log(message, ERROR))
        saveLogs()
    }

    fun getLogs(): MutableList<Log> {
        return Paper.book().read(LOGS_LIST_KEY)
    }

    private fun saveLogs() {
        Paper.book().write(LOGS_LIST_KEY, logsList)
    }

    fun clearLogs() {
        logsList.clear()

        init()
    }
}