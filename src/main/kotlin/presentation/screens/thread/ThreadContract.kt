package presentation.screens.thread

import model.message.Message

data class MessagesUiState(
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)

sealed interface CompletionUiState {
    object Idle : CompletionUiState
    data class InProgress(val message: String) : CompletionUiState
    data class Error(val message: String) : CompletionUiState

    fun isInProgress() : Boolean {
        return when (this) {
            is InProgress -> true
            else -> false
        }
    }
}
