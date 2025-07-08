package simple.compose.digfinder.finder

import simple.compose.digfinder.data.DuplicateFile

sealed interface FinderAction {

    data object Back : FinderAction

    data class NavToResult(val duplicateFiles: MutableList<DuplicateFile>) : FinderAction


}