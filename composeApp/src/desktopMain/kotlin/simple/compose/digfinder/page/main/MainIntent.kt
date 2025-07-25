package simple.compose.digfinder.page.main

import simple.compose.digfinder.base.BaseIntent

sealed interface MainIntent : BaseIntent {

   data object GetList : MainIntent

   data class AddProject(val projectName: String, val projectPath: String) : MainIntent

   data class DeleteProject(val projectId: Long) : MainIntent

}