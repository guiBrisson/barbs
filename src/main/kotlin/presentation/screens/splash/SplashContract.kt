package presentation.screens.splash

import model.assistant.AssistantData

sealed interface AssistantUiState {
    object Init : AssistantUiState
    object Loading : AssistantUiState
    data class Error(val message: String) : AssistantUiState
    data class Success(val assistant: AssistantData) : AssistantUiState

    fun hasError(): Boolean {
        return when (this) {
            is Error -> true
            else -> false
        }
    }

    fun isSuccess(): Boolean {
        return when (this) {
            is Success -> true
            else -> false
        }
    }

    fun isLoading(): Boolean {
        return when (this) {
            is Loading -> true
            else -> false
        }
    }
}