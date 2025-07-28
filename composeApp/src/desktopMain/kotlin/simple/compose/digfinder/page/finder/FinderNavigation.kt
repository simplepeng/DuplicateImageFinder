package simple.compose.digfinder.page.finder

import simple.compose.digfinder.base.BaseNavigation

sealed interface FinderNavigation : BaseNavigation{

    data object Back : FinderNavigation

}