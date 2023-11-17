package data.network.repository

import data.network.dto.CompletionBody
import domain.repository.ChatCompletionRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.completion.ChatCompletion
import model.ResultOf

class ChatCompletionRepositoryImpl(
    private val client: HttpClient
) : ChatCompletionRepository {

    override suspend fun completion(prompt: String): Flow<ResultOf<ChatCompletion>> = flow {
        try {
            val completion = client.post("chat/completions") {
                setBody(CompletionBody(prompt))
            }.body<ChatCompletion>()
            emit(ResultOf.Success(completion))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultOf.Failure(e))
        }
    }
}
