package simple.compose.digfinder.data

import kotlinx.serialization.Serializable

@Serializable
data class DuplicateFile(
    val file1: File,
    val file2: File,
) {
    @Serializable
    data class File(
        val path: String,
        val name: String,
        val size: Long,
    )
}
