package simple.compose.digfinder.page.main

sealed interface MainUIState {

    data object Loading : MainUIState

    data object Content : MainUIState

    data object AddProjectDialog: MainUIState
}