package simple.compose.digfinder.page.main

import androidx.lifecycle.viewModelScope
import database.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import simple.compose.digfinder.base.BaseViewModel
import simple.compose.digfinder.db.DbHelper

class MainViewModel : BaseViewModel<MainAction, MainUIState, MainIntent>(MainUIState.Content) {

    override fun performIntent(intent: MainIntent) {

    }

    private val _projectList = MutableStateFlow<List<Project>>(emptyList())
    val projectList = _projectList.asStateFlow()

    fun getList() {
        updateUIState(MainUIState.Loading)
        viewModelScope.launch {
            val projectList = DbHelper.getList()
            _projectList.value = projectList
            updateUIState(MainUIState.Content)
        }
    }

    private val _dialogState = MutableStateFlow<MainDialogState>(MainDialogState.None)
    val dialogState = _dialogState.asStateFlow()

    fun updateDialogState(state: MainDialogState) {
        _dialogState.value = state
    }

    fun addProject(projectName: String, projectPath: String) {
        DbHelper.add(projectName, projectPath)
        getList()
    }

}