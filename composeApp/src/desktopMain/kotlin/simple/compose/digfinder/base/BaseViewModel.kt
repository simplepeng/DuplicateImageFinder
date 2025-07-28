package simple.compose.digfinder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import simple.compose.digfinder.page.main.MainDialogState

abstract class BaseViewModel<I : BaseIntent, N : BaseNavigation, S : BaseUIState, D : BaseDialogState>(
   initUIState: S,
   initDialogState: D,
) : ViewModel() {

   abstract fun performIntent(intent: I)

   private val _navigationState = MutableSharedFlow<N>()
   val navigationState = _navigationState.asSharedFlow()

   fun performNavigation(navigation: N) {
      viewModelScope.launch {
         _navigationState.emit(navigation)
      }
   }

   private val _uiState = MutableStateFlow(initUIState)
   val uiState = _uiState.asStateFlow()

   fun updateUIState(state: S) {
      _uiState.value = state
   }

   private val _dialogState = MutableStateFlow<D>(initDialogState)
   val dialogState = _dialogState.asStateFlow()

   fun updateDialogState(state: D) {
      _dialogState.value = state
   }

}