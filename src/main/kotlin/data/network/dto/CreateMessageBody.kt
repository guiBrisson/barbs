package data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageBody(
    val role: String,
    val content: String,
) {
    constructor(prompt: String) : this(
        role = "user",
        content = prompt
    )
}
