package data.network.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletionBody(
    @SerialName("messages")
    val messages: List<CompletionMessageBody>,
    @SerialName("model")
    val model: String,
    @SerialName("stream")
    val stream: Boolean,
) {
    constructor(prompt: String) : this(
        messages = listOf(CompletionMessageBody(prompt)),
        model = "gpt-3.5-turbo",
        stream = false,
    )
}

@Serializable
data class CompletionMessageBody(
    @SerialName("content")
    val content: String,
    @SerialName("role")
    val role: String
) {
    constructor(content: String) : this(
        content = content,
        role = "system"
    )
}