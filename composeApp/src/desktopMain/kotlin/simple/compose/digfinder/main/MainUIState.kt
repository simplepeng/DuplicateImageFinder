package simple.compose.digfinder.main

sealed interface MainUIState {

    data object Loading : MainUIState

    data object Content : MainUIState

    data object AddProjectDialog: MainUIState
}