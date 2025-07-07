package simple.compose.digfinder

import kotlinx.serialization.Serializable

sealed interface Router {

    @Serializable
    object Main : Router

    @Serializable
    object Finder : Router

    @Serializable
    object Result : Router

}