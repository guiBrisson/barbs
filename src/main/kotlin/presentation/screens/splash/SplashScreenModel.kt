package presentation.screens.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.local.Assistant
import domain.repository.AssistantsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ResultOf

class SplashScreenModel(
    private val assistantsRepository: AssistantsRepository,
) : ScreenModel {
    private val _assistantUiState = MutableStateFlow<AssistantUiState>(AssistantUiState.Init)
    val assistantUiState: StateFlow<AssistantUiState> = _assistantUiState.asStateFlow()

    fun fetchAssistants() {
        screenModelScope.launch(Dispatchers.IO) {
            _assistantUiState.update { AssistantUiState.Loading }
            when (val result = assistantsRepository.listAssistants()) {
                is ResultOf.Failure -> {
                    _assistantUiState.update { AssistantUiState.Error(result.exception?.message ?: "Unexpected Error") }
                }

                is ResultOf.Success -> {
                    val assistant = result.value.data.first()
                    Assistant.current = assistant
                    _assistantUiState.update { AssistantUiState.Success(assistant) }
                }
            }
        }
    }
}
