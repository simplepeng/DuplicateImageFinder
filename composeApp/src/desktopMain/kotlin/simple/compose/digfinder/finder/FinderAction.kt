package simple.compose.digfinder.finder

import simple.compose.digfinder.data.DuplicateFile

sealed interface FinderAction {

    data class NavToResult(val duplicateFiles: List<DuplicateFile>) : FinderAction


}