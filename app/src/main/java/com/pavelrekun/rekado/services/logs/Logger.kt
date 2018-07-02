package com.pavelrekun.rekado.services.logs

import com.orhanobut.hawk.Hawk
import com.pavelrekun.rekado.data.Log

object Logger {

    private const val LOGS_LIST_KEY = "LOGS_LIST_KEY"

    private lateinit var logsList: MutableList<Log>

    fun init() {
        logsList = ArrayList()
        log(1, "Application started!")
        saveLogs()
    }

    fun log(type: Int, message: String) {
        logsList.add(Log(message, type))
        saveLogs()
    }

    fun getLogs(): MutableList<Log> {
        return Hawk.get(LOGS_LIST_KEY)
    }

    private fun saveLogs() {
        Hawk.put(LOGS_LIST_KEY, logsList)
    }
}