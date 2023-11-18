package model.thread.run

import kotlinx.serialization.Serializable

@Serializable
data class RunError(
    val code: String,
    val message: String,
)
