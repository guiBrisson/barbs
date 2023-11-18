package model.thread.run.step


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunStepDetails(
    @SerialName("message_creation")
    val messageCreation: RunMessageCreation,
    @SerialName("type")
    val type: String
)