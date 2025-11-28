package com.pavelrekun.rekado.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.compose.NavigationUiState.Loading
import com.pavelrekun.rekado.compose.NavigationUiState.Success
import com.pavelrekun.rekado.core.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
) : ViewModel() {
    val uiState: StateFlow<NavigationUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}