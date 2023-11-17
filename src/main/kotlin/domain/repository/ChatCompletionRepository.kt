package domain.repository

import kotlinx.coroutines.flow.Flow
import model.ChatCompletion

interface ChatCompletionRepository {
    suspend fun completion(prompt: String): Flow<ChatCompletion>
}