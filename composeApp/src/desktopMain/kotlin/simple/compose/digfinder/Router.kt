package simple.compose.digfinder

import kotlinx.serialization.Serializable
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.data.ResultWrapper

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data object Finder : Router

    @Serializable
     class Result(val path: String) : Router

}