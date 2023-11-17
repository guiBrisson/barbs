package domain.repository

import kotlinx.coroutines.flow.Flow
import model.ChatCompletion
import model.ResultOf

interface ChatCompletionRepository {
    suspend fun completion(prompt: String): Flow<ResultOf<ChatCompletion>>
}