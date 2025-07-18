package simple.compose.digfinder.config

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import simple.compose.digfinder.db.ProjectMapper

sealed interface Router {

    @Serializable
    data object Main : Router

    @Serializable
    data class Finder(val mapper: ProjectMapper) : Router

}