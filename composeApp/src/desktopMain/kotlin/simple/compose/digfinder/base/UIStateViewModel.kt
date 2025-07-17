package simple.compose.digfinder.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class UIStateViewModel<UIState : BaseUIState>(
    initUIState: UIState
) : ViewModel() {

    private val _uiState = MutableStateFlow(initUIState)
    val uiState = _uiState.asStateFlow()

    open fun updateUIState(state: UIState) {
        _uiState.value = state
    }


}