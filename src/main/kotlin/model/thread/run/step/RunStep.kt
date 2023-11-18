package model.thread.run.step


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.thread.run.RunError

@Serializable
data class RunStep(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val objectX: String,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("assistant_id")
    val assistantId: String,
    @SerialName("thread_id")
    val threadId: String,
    @SerialName("run_id")
    val runId: String,
    @SerialName("type")
    val type: String,
    @SerialName("status")
    val status: String,
    @SerialName("step_details")
    val stepDetails: RunStepDetails,
    @SerialName("last_error")
    val lastError: RunError?,
    @SerialName("cancelled_at")
    val cancelledAt: Long?,
    @SerialName("completed_at")
    val completedAt: Long?,
    @SerialName("expires_at")
    val expiresAt: Long?,
    @SerialName("failed_at")
    val failedAt: Long?,
)