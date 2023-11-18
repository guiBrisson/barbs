package domain.repository

import model.ResultOf
import model.message.Message
import model.message.MessageList

interface MessageRepository {
    suspend fun getMessage(threadId: String, messageId: String): ResultOf<Message>
    suspend fun listMessage(threadId: String): ResultOf<MessageList>
    suspend fun createMessage(threadId: String, prompt: String): ResultOf<Message>
}
