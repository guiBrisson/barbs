package model.message


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageContent(
    @SerialName("type")
    val type: String,
    @SerialName("text")
    val text: MessageText
)