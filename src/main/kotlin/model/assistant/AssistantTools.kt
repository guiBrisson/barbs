package model.assistant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantTools(
    @SerialName("type")
    val type: String,
)