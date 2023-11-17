package model.assistant


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantData(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val objectX: String,
    @SerialName("created_at")
    val createdAt: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("model")
    val model: String,
    @SerialName("instructions")
    val instructions: String,
    @SerialName("tools")
    val tools: List<AssistantTools>,
)