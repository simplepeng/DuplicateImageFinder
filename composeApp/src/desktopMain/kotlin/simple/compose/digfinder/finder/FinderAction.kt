package simple.compose.digfinder.finder

sealed interface FinderAction {

    data object Back : FinderAction

}