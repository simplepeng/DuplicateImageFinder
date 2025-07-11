package simple.compose.digfinder.page.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _actionState = MutableSharedFlow<MainAction>()
    val actionState = _actionState.asSharedFlow()

    fun doAction(action: MainAction) {
//        _actionState.tryEmit(action)
        viewModelScope.launch {
            _actionState.emit(action)
        }
    }

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Content)
    val uiState = _uiState.asStateFlow()

    fun updateUIState(state: MainUIState) {
        viewModelScope.launch {
            _uiState.tryEmit(state)
        }
    }

    fun addProject(projectName: String, projectPath: String){

    }
}