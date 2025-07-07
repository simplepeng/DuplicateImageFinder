package simple.compose.digfinder.data

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val resourcePath: String,
)
