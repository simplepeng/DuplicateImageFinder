package simple.compose.digfinder.config

import kotlinx.serialization.Serializable

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data class Finder(val json: String) : Router

}