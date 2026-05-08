package com.moseswn.kurahubke.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val isOffline: Boolean = false,
    val electionCountdown: String = "",
    val lastUpdated: String = "",
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {

                delay(1500)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isOffline = false,
                    electionCountdown = "98 Days Remaining",
                    lastUpdated = "Updated Today"
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun setOfflineMode(isOffline: Boolean) {

        _uiState.value = _uiState.value.copy(
            isOffline = isOffline
        )
    }

    fun refreshHomeData() {

        loadHomeData()
    }
}