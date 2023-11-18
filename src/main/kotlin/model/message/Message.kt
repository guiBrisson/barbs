package model.message


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val objectX: String,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("thread_id")
    val threadId: String,
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: List<MessageContent>,
    @SerialName("assistant_id")
    val assistantId: String?,
    @SerialName("run_id")
    val runId: String?,
)