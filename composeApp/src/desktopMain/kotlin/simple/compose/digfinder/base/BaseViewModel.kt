package simple.compose.digfinder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<I : BaseIntent, N : BaseNavigation, S : BaseUIState>(
   initUIState: S
) : ViewModel() {

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

   abstract fun performIntent(intent: I)

}