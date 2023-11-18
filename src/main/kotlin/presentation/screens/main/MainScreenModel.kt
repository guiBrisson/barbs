package presentation.screens.main

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.repository.ThreadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ResultOf

class MainScreenModel(
    private val threadRepository: ThreadRepository,
) : ScreenModel {
    private val _threadsUiState = MutableStateFlow<ThreadsUiState>(ThreadsUiState.Init)
    val threadsUiState: StateFlow<ThreadsUiState> = _threadsUiState.asStateFlow()

    private val _newThreadUiState = MutableStateFlow<NewThreadUiState>(NewThreadUiState.Idle)
    val newThreadUiState: StateFlow<NewThreadUiState> = _newThreadUiState.asStateFlow()

    fun fetchThreadList() {
        screenModelScope.launch(Dispatchers.IO) {
            _threadsUiState.update { ThreadsUiState.Loading }

            when (val result = threadRepository.listThreads()) {
                is ResultOf.Success -> {
                    _threadsUiState.update { ThreadsUiState.Success(result.value) }
                }
                is ResultOf.Failure -> {
                    _threadsUiState.update { ThreadsUiState.Error(result.exception?.message ?: "Unexpected Error") }
                }
            }
        }
    }

    fun newThread() {
        screenModelScope.launch(Dispatchers.IO) {
            _newThreadUiState.update { NewThreadUiState.Loading }

            when (val result = threadRepository.createThread()) {
                is ResultOf.Success -> {
                    fetchThreadList()
                    _newThreadUiState.update { NewThreadUiState.Success }
                }
                is ResultOf.Failure -> {
                    _newThreadUiState.update { NewThreadUiState.Error(result.exception?.message ?: "Unexpected Error") }
                }
            }
        }
    }

}
