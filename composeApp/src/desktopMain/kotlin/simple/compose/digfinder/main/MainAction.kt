package simple.compose.digfinder.main

sealed interface MainAction {

    data object NavToFinder : MainAction

}