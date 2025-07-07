package simple.compose.digfinder.finder

sealed interface FinderIntent {

    data class AddPath(val path: String) : FinderIntent

    data class Scan(val pathList: List<String>) : FinderIntent

}