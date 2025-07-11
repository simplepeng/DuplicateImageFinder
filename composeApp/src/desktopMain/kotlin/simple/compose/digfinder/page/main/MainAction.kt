package simple.compose.digfinder.page.main

import simple.compose.digfinder.base.BaseAction

sealed interface MainAction : BaseAction{

    data object NavToFinder : MainAction

}