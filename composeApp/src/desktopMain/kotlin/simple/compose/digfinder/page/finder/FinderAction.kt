package simple.compose.digfinder.page.finder

sealed interface FinderAction {

    data object Back : FinderAction

}