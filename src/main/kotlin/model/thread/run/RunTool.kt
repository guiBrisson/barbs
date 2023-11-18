package model.thread.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunTool(
    @SerialName("type")
    val type: String
)