package simple.compose.digfinder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch 

abstract class BaseViewModel<Action : BaseAction, State : BaseUIState, Intent : BaseIntent> : ViewModel() {

    private val _actionState = MutableSharedFlow<Action>()
    val actionState = _actionState.asSharedFlow()

    fun doAction(action: Action) {
        viewModelScope.launch {
            _actionState.emit(action)
        }
    }

    private val _uiState = MutableStateFlow(BaseUIState.Default)
    val uiState = _uiState.asStateFlow()

    abstract fun updateUIState(state: State)

    abstract fun performIntent(intent: Intent)
}