package simple.compose.digfinder.page.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import simple.compose.digfinder.base.BaseViewModel

class MainViewModel : BaseViewModel<MainAction, MainUIState, MainIntent>(MainUIState.Content) {

    override fun performIntent(intent: MainIntent) {

    }

    fun addProject(projectName: String, projectPath: String) {
        doAction(MainAction.NavToFinder)
        updateUIState(MainUIState.Content)
    }

}