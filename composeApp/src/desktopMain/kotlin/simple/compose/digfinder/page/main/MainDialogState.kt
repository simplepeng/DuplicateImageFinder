package simple.compose.digfinder.page.main

import database.Project

sealed interface MainDialogState {

    data object None : MainDialogState

    data object AddProjectDialog : MainDialogState

    data class DeleteProjectDialog(val project: Project) : MainDialogState
}