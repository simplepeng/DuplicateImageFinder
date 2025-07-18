package simple.compose.digfinder.page.main

import database.Project
import simple.compose.digfinder.base.BaseAction

sealed interface MainAction : BaseAction {

    data class NavToFinder(val project: Project) : MainAction

}