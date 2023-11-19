package presentation.screens.thread

import model.message.Message

data class MessagesUiState(
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)
