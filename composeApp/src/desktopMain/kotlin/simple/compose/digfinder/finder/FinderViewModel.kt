package simple.compose.digfinder.finder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FinderViewModel : ViewModel() {

    fun performIntent(intent: FinderIntent) {
        when (intent) {
            is FinderIntent.AddPath -> addPath(intent.path)
            is FinderIntent.Scan -> scan(intent.pathList)
        }
    }

    private val _pathList = MutableStateFlow<List<String>>(emptyList())
    val pathList = _pathList.asStateFlow()

    private fun addPath(path: String) {
        if (path.isEmpty()) {
            return
        }
        if (_pathList.value.contains(path)) {
            return
        }
        _pathList.value += path
    }

    private fun scan(pathList: List<String>) {

    }
}