package simple.compose.digfinder.page.finder

import simple.compose.digfinder.base.BaseAction

sealed interface FinderAction : BaseAction{

    data object Back : FinderAction

}