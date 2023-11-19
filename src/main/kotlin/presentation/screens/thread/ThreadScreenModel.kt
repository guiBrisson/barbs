package presentation.screens.thread

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.local.Assistant
import domain.repository.MessageRepository
import domain.repository.RunThreadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ResultOf
import model.message.Message
import kotlin.time.Duration.Companion.seconds

class ThreadScreenModel(
    private val messageRepository: MessageRepository,
    private val runThreadRepository: RunThreadRepository,
) : ScreenModel {
    private val assistantId: String? = Assistant.current?.id
    var threadId: String? = null

    private val _messagesUiState = MutableStateFlow(MessagesUiState())
    val messagesUiState: StateFlow<MessagesUiState> = _messagesUiState.asStateFlow()

    private val _completionUiState = MutableStateFlow<CompletionUiState>(CompletionUiState.Idle)
    val completionUiState: StateFlow<CompletionUiState> = _completionUiState.asStateFlow()

    fun addMessage(prompt: String) {
        screenModelScope.launch(Dispatchers.IO) {
            threadId?.let { threadId ->
                when (val result = messageRepository.createMessage(threadId, prompt)) {
                    is ResultOf.Success -> {
                        println("THREAD_MODEL: message added")
                        appendMessageOnMessageList(result.value)
                        runThread(threadId)
                    }

                    is ResultOf.Failure -> Unit // Todo: handle failure
                }
            }
        }
    }

    private fun runThread(threadId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            assistantId?.let { assistantId ->
                when (val result = runThreadRepository.runThread(assistantId, threadId)) {
                    is ResultOf.Success -> {
                        checkThreadRunSteps(threadId, result.value.id)
                        _completionUiState.update { CompletionUiState.InProgress("Thread is running") }
                    }

                    is ResultOf.Failure -> {
                        val errorMessage = result.exception?.message ?: "Unexpected Error"
                        _completionUiState.update { CompletionUiState.Error(errorMessage) }
                    }
                }
            }
        }
    }

    private fun checkThreadRunSteps(threadId: String, runId: String) {
        val milliDelay = 2.seconds
        screenModelScope.launch(Dispatchers.IO) {
            when (val result = runThreadRepository.runStepList(threadId, runId)) {
                is ResultOf.Success -> {
                    result.value.data.ifEmpty {
                        _completionUiState.update { CompletionUiState.InProgress("Run step is empty") }
                        delay(milliDelay)
                        checkThreadRunSteps(threadId, runId)
                        return@launch
                    }

                    val firstStep = result.value.data.first()

                    if (firstStep.status.contains("in_progress")) {
                        _completionUiState.update { CompletionUiState.InProgress("Run step is in progress") }
                        delay(milliDelay)
                        checkThreadRunSteps(threadId, runId)
                        return@launch
                    } else if (firstStep.status.contains("completed")) {
                        _completionUiState.update { CompletionUiState.InProgress("Run step is complete") }
                        fetchMessage(threadId, firstStep.stepDetails.messageCreation.messageId)
                    }
                }

                is ResultOf.Failure -> {
                    val errorMessage = result.exception?.message ?: "Unexpected Error"
                    _completionUiState.update { CompletionUiState.Error(errorMessage) }
                }
            }

        }
    }

    private fun fetchMessage(threadId: String, messageId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            when (val result = messageRepository.getMessage(threadId, messageId)) {
                is ResultOf.Success -> {
                    _completionUiState.update { CompletionUiState.Idle }
                    appendMessageOnMessageList(result.value)
                }

                is ResultOf.Failure -> {
                    val errorMessage = result.exception?.message ?: "Unexpected Error"
                    _completionUiState.update { CompletionUiState.Error(errorMessage) }
                }
            }
        }
    }

    private fun appendMessageOnMessageList(message: Message) {
        val messages = _messagesUiState.value.messages.toMutableList()
        messages.add(0, message)

        _messagesUiState.update {
            _messagesUiState.value.copy(messages = messages)
        }
    }

    fun fetchMessages(threadId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            _messagesUiState.update {
                _messagesUiState.value.copy(
                    loading = true,
                    error = null,
                    messages = emptyList()
                )
            }
            when (val result = messageRepository.listMessage(threadId)) {
                is ResultOf.Success -> {
                    _messagesUiState.update {
                        _messagesUiState.value.copy(loading = false, error = null, messages = result.value.data)
                    }
                }

                is ResultOf.Failure -> {
                    _messagesUiState.update {
                        val errorMessage = result.exception?.message ?: "Unexpected Error"
                        _messagesUiState.value.copy(loading = false, error = errorMessage)
                    }
                }
            }
        }
    }


}
