package com.pavelrekun.rekado.services.logs

import com.pavelrekun.rekado.data.Log
import io.paperdb.Paper

object LogHelper {

    private const val LOGS_LIST_KEY = "LOGS_LIST_KEY"

    const val INFO = 1
    const val ERROR = 0

    private lateinit var logsList: MutableList<Log>

    fun init() {
        logsList = ArrayList()
        log(INFO, "Application started!")
        saveLogs()
    }

    fun log(type: Int, message: String) {
        logsList.add(Log(message, type))
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