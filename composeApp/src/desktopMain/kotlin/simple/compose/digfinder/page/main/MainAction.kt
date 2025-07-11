package simple.compose.digfinder.page.main

sealed interface MainAction {

    data object NavToFinder : MainAction

}