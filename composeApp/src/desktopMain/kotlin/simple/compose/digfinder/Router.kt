package simple.compose.digfinder

import kotlinx.serialization.Serializable
import simple.compose.digfinder.data.DuplicateFile

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data object Finder : Router

    @Serializable
    class Result(val duplicateFiles: List<DuplicateFile>) : Router

}