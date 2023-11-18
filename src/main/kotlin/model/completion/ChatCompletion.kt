package model.completion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletion(
    @SerialName("choices")
    val choices: List<ChatCompletionChoice>,
    @SerialName("created")
    val created: Long,
    @SerialName("id")
    val id: String,
    @SerialName("model")
    val model: String,
    @SerialName("object")
    val objectX: String,
    @SerialName("usage")
    val usage: ChatCompletionUsage,
)