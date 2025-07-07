package simple.compose.digfinder.finder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FinderViewModel : ViewModel() {

    fun performIntent(intent: FinderIntent) {
        when (intent) {
            is FinderIntent.AddPath -> addPath(intent)
            is FinderIntent.Scan -> TODO()
        }
    }

    private val _pathList = MutableStateFlow<List<String>>(emptyList())
    val pathList = _pathList.asStateFlow()

    private fun addPath(intent: FinderIntent.AddPath) {
        val path = intent.path
//        if (_pathList.value.contains(path)) {
//            return
//        }
        _pathList.value += path
    }
}