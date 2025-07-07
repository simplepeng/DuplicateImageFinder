package simple.compose.digfinder.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : ViewModel() {

    private val _actionState = MutableSharedFlow<MainAction>()
    val actionState = _actionState.asSharedFlow()

    fun doAction(action: MainAction){
        _actionState.tryEmit(action)
    }
}