package simple.compose.digfinder

import kotlinx.serialization.Serializable
import simple.compose.digfinder.data.DuplicateFile

sealed interface Router {

    @Serializable
    object Main : Router

    @Serializable
    object Finder : Router

    @Serializable
    data class Result(val duplicateFiles: List<DuplicateFile>) : Router

}