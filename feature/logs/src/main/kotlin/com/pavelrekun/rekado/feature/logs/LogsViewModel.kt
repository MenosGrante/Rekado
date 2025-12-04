package com.pavelrekun.rekado.feature.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.core.common.logger.Logger
import com.pavelrekun.rekado.core.model.logs.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val logger: Logger
) : ViewModel() {

    val logs: StateFlow<PersistentList<Log>> = logger.logFlow
        .map { it.toPersistentList() }
        .stateIn(
            scope = viewModelScope,
            initialValue = persistentListOf(),
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun clearLogs() {
        logger.clearLogs()
    }
}