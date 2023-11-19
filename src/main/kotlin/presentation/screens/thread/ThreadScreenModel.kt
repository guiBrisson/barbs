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
                        println("THREAD_MODEL: thread is running")
                        checkThreadRunSteps(threadId, result.value.id)
                    }

                    is ResultOf.Failure -> Unit // Todo: handle failure
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
                        println("THREAD_MODEL: run step is empty")
                        delay(milliDelay)
                        checkThreadRunSteps(threadId, runId)
                        return@launch
                    }

                    val firstStep = result.value.data.first()

                    if (firstStep.status.contains("in_progress")) {
                        println("THREAD_MODEL: run step is in progress")
                        delay(milliDelay)
                        checkThreadRunSteps(threadId, runId)
                        return@launch
                    } else if (firstStep.status.contains("completed")) {
                        println("THREAD_MODEL: run step is complete")
                        fetchMessage(threadId, firstStep.stepDetails.messageCreation.messageId)
                    }
                }

                is ResultOf.Failure -> Unit // Todo: handle failure
            }

        }
    }

    private fun fetchMessage(threadId: String, messageId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            when (val result = messageRepository.getMessage(threadId, messageId)) {
                is ResultOf.Success -> {
                    val fullMessage = result.value.content.map { it.text.value }
                    println("THREAD_MODEL: Completion: $fullMessage")
                    appendMessageOnMessageList(result.value)
                }
                is ResultOf.Failure -> Unit // Todo: handle failure
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
            _messagesUiState.update { _messagesUiState.value.copy(loading = true, error = null, messages = emptyList()) }
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
