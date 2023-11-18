package model.message


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageText(
    @SerialName("value")
    val value: String
)