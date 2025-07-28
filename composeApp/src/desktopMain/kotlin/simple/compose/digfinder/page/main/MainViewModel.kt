package simple.compose.digfinder.page.main

import androidx.lifecycle.viewModelScope
import database.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import simple.compose.digfinder.base.BaseViewModel
import simple.compose.digfinder.db.DbHelper

class MainViewModel : BaseViewModel<MainIntent, MainNavigation, MainUIState, MainDialogState>(
   initUIState = MainUIState.Content,
   initDialogState = MainDialogState.None
) {

   override fun performIntent(intent: MainIntent) {
      when (intent) {
         MainIntent.GetList -> getList()
         is MainIntent.AddProject -> addProject(intent.projectName, intent.projectPath)
         is MainIntent.DeleteProject -> deleteProject(intent.projectId)
      }
   }

   private val _projectList = MutableStateFlow<List<Project>>(emptyList())
   val projectList = _projectList.asStateFlow()

   private fun getList() {
      updateUIState(MainUIState.Loading)
      viewModelScope.launch {
         val projectList = DbHelper.getProjectList()
         _projectList.value = projectList
         updateUIState(MainUIState.Content)
      }
   }

   private fun addProject(projectName: String, projectPath: String) {
      if (projectName.isEmpty()) return

      viewModelScope.launch {
         DbHelper.addProject(projectName, projectPath).also {
            updateDialogState(MainDialogState.None)
            getList()
         }
      }
   }

   private fun deleteProject(id: Long) {
      viewModelScope.launch {
         DbHelper.deleteProject(id).also {
            getList()
         }
      }
   }
}