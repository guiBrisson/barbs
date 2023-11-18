package model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageList(
    @SerialName("object")
    val objectX: String,
    val data: List<Message>,
    @SerialName("first_id")
    val firstId: String,
    @SerialName("last_id")
    val lastId: String,
    @SerialName("has_more")
    val hasMore: Boolean,
)
