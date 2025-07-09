package simple.compose.digfinder.finder

import simple.compose.digfinder.data.PathWrapper

sealed interface FinderIntent {

    data class AddPath(val path: String) : FinderIntent

    data class Scan(val pathList: List<PathWrapper>) : FinderIntent

    data class Watching(val pathList: List<PathWrapper>) : FinderIntent

    data class UpdateChecked(val index: Int, val isChecked: Boolean) : FinderIntent
}