package simple.compose.digfinder.data

import database.ProjectDirs

data class PathWrapper(
    val projectDirs: ProjectDirs,
    val isChecked: Boolean = true
)
