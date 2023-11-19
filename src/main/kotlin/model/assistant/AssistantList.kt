package model.assistant


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantList(
    @SerialName("object")
    val objectX: String,
    @SerialName("data")
    val data: List<AssistantData>,
    @SerialName("first_id")
    val firstId: String?,
    @SerialName("last_id")
    val lastId: String?,
    @SerialName("has_more")
    val hasMore: Boolean
)