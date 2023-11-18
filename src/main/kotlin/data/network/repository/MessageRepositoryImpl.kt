package data.network.repository

import data.network.dto.CreateMessageBody
import data.network.utils.isSuccessful
import domain.repository.MessageRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.ResultOf
import model.message.Message
import model.message.MessageList

class MessageRepositoryImpl(
    private val client: HttpClient,
): MessageRepository {

    override suspend fun getMessage(threadId: String, messageId: String): ResultOf<Message> {
        val response = client.get("threads/$threadId/messages/$messageId") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val responseBody = response.body<Message>()
            ResultOf.Success(responseBody)
        } else {
            ResultOf.Failure(null)
        }
    }

    override suspend fun listMessage(threadId: String): ResultOf<MessageList> {
        val response = client.get("threads/$threadId/messages") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val body = response.body<MessageList>()
            ResultOf.Success(body)
        } else {
            ResultOf.Failure(null)
        }
    }

    override suspend fun createMessage(threadId: String, prompt: String): ResultOf<Message> {
        val body = CreateMessageBody(prompt)
        val response = client.post("threads/$threadId/messages") {
            header("OpenAI-Beta", "assistants=v1")
            setBody(body)
        }

        return if (response.isSuccessful()) {
            val responseBody = response.body<Message>()
            ResultOf.Success(responseBody)
        } else {
            ResultOf.Failure(null)
        }
    }
}