package simple.compose.digfinder.finder

sealed interface FinderUIState {

    data object Default : FinderUIState
    data object Scanning : FinderUIState
    data object DuplicateFilesIsEmpty : FinderUIState

}