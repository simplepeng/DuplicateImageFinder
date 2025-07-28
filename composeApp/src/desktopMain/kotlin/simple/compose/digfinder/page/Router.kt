package simple.compose.digfinder.page

import kotlinx.serialization.Serializable

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data class Finder(val id: Long) : Router

}