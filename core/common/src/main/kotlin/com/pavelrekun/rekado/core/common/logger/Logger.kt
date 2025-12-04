package com.pavelrekun.rekado.core.common.logger

import com.pavelrekun.rekado.core.model.logs.Log
import com.pavelrekun.rekado.core.model.logs.LogLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Logger internal constructor() {

    private val _logFlow = MutableStateFlow<List<Log>>(emptyList())
    val logFlow: StateFlow<List<Log>> = _logFlow.asStateFlow()

    fun i(message: String) = addLog(LogLevel.INFO, message)

    fun e(message: String) = addLog(LogLevel.ERROR, message)

    fun clearLogs() {
        _logFlow.value = emptyList()
    }

    private fun addLog(
        level: LogLevel,
        message: String
    ) {
        val entry = Log(
            timestamp = System.currentTimeMillis(),
            level = level,
            message = message
        )

        _logFlow.update { currentList ->
            currentList + entry
        }
    }
}