package simple.compose.digfinder.page.main

import database.Project
import simple.compose.digfinder.base.BaseNavigation

sealed interface MainNavigation : BaseNavigation {

    data class ToFinder(val project: Project) : MainNavigation

}