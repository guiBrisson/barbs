package model.completion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionMessage(
    @SerialName("content")
    val content: String?,
    @SerialName("role")
    val role: String
)