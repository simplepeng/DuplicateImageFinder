package simple.compose.digfinder.data

import kotlinx.serialization.Serializable

@Serializable
data class DuplicateFile(
    val path1: String,
    val path2: String,
)
