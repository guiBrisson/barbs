package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionChoice(
    @SerialName("finish_reason")
    val finishReason: String?,
    @SerialName("index")
    val index: Int,
    @SerialName("message")
    val message: ChatCompletionMessage
)