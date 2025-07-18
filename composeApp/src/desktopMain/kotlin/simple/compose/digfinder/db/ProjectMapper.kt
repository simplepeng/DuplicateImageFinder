package simple.compose.digfinder.db

import database.Project
import kotlinx.serialization.Serializable

@Serializable
data class ProjectMapper(
    val id: Long = 0,
    val name: String = "",
    val path: String = "",
    val time: Long = 0,
) {
//    companion object {
//        fun from(project: Project) = ProjectMapper(
//            id = project.id,
//            name = project.name,
//            path = project.path,
//            time = project.time
//        )
//    }

    fun toProject() = Project(
        id = this.id,
        name = this.name,
        path = this.path,
        time = this.time,
    )
}
