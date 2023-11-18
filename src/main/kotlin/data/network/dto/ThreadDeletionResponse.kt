package data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreadDeletionResponse(
    val id: String,
    @SerialName("object")
    val objectX: String,
    val deleted: Boolean,
)
