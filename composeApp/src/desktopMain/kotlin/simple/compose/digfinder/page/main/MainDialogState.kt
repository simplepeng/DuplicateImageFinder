package simple.compose.digfinder.page.main

sealed interface MainDialogState {

    data object None : MainDialogState

    data object AddProjectDialog : MainDialogState

}