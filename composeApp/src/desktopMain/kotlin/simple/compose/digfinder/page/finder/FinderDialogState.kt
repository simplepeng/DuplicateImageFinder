package simple.compose.digfinder.page.finder

import simple.compose.digfinder.data.DuplicateFile

sealed interface FinderDialogState {

    data object None : FinderDialogState

    data class Result(val duplicateFiles: List<DuplicateFile>) : FinderDialogState
}