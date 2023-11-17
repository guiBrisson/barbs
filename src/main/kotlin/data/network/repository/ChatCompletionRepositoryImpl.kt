package data.network.repository

import data.network.KtorClient
import data.network.dto.CompletionBody
import domain.repository.ChatCompletionRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.ChatCompletion

class ChatCompletionRepositoryImpl : ChatCompletionRepository {
    private val client = KtorClient.client

    override suspend fun completion(prompt: String): Flow<ChatCompletion> = flow {
        val completion = client.post("chat/completions") {
            setBody(CompletionBody(prompt))
        }.body<ChatCompletion>()
//        println(completion)
        emit(completion)
    }

//    override suspend fun completion(prompt: String): Flow<ChatCompletion> = flow {
//        client.preparePost("chat/completions") {
//            setBody(CompletionBody(prompt))
//        }.execute { httpResponse ->
//            val channel: ByteReadChannel = httpResponse.body()
//            while (!channel.isClosedForRead) {
//                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
//                while (packet.isNotEmpty) {
//                    val stringJsonResponse = packet.readUTF8Line()
//                    stringJsonResponse?.removePrefix("data: ").also(::println)
////                    val response = json.decodeFromString<DataCompletionBody>(stringJsonResponse)
////                    val completion = response.data
////                    emit(completion)
//                }
//            }
//        }
//    }
}
