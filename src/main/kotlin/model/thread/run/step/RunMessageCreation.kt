package model.thread.run.step


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunMessageCreation(
    @SerialName("message_id")
    val messageId: String
)