package model.thread.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunThread(
    @SerialName("assistant_id")
    val assistantId: String,
    @SerialName("cancelled_at")
    val cancelledAt: Long?,
    @SerialName("completed_at")
    val completedAt: Long?,
    @SerialName("created_at")
    val createdAt: Int,
    @SerialName("expires_at")
    val expiresAt: Long?,
    @SerialName("failed_at")
    val failedAt: Long?,
    @SerialName("id")
    val id: String,
    @SerialName("instructions")
    val instructions: String?,
    @SerialName("last_error")
    val lastError: RunError?,
    @SerialName("model")
    val model: String,
    @SerialName("object")
    val objectX: String,
    @SerialName("started_at")
    val startedAt: Long,
    @SerialName("status")
    val status: String,
    @SerialName("thread_id")
    val threadId: String,
    @SerialName("tools")
    val tools: List<RunTool>
)