package simple.compose.digfinder.config

import kotlinx.serialization.Serializable
import simple.compose.digfinder.data.DuplicateFile

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data object Finder : Router

}