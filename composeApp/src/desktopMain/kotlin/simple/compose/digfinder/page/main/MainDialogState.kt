package simple.compose.digfinder.page.main

import database.Project
import simple.compose.digfinder.base.BaseDialogState

sealed interface MainDialogState : BaseDialogState {

    data object None : MainDialogState

    data object AddProject : MainDialogState

    data class DeleteProject(val project: Project) : MainDialogState
}