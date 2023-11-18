package presentation.screens.main

import model.thread.Thread

sealed interface ThreadsUiState {
    object Init : ThreadsUiState
    object Loading : ThreadsUiState
    data class Error(val message: String) : ThreadsUiState
    data class Success(val list: List<Thread>) : ThreadsUiState

    fun isSuccess(): Boolean {
        return when (this) {
            is Success -> true
            else -> false
        }
    }

    fun isLoading(): Boolean {
        return when (this) {
            Loading -> true
            else -> false
        }
    }
}

sealed interface NewThreadUiState {
    object Idle : NewThreadUiState
    object Loading : NewThreadUiState
    data class Error(val message: String) : NewThreadUiState
    object Success : NewThreadUiState

    fun isLoading(): Boolean {
        return when (this) {
            Loading -> true
            else -> false
        }
    }
}