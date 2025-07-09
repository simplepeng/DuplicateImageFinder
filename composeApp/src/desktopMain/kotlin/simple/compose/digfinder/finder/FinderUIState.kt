package simple.compose.digfinder.finder

import simple.compose.digfinder.data.DuplicateFile

sealed interface FinderUIState {

    data object Default : FinderUIState

    data object Scanning : FinderUIState

    data object DuplicateFilesIsEmpty : FinderUIState

    data class ShowResultDialog(val duplicateFiles: List<DuplicateFile>) : FinderUIState

    data object Watching : FinderUIState
}