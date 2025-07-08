package simple.compose.digfinder.finder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.ext.hashStr
import java.io.File
import kotlin.collections.forEach
import kotlin.collections.orEmpty

class FinderViewModel : ViewModel() {

    private val _actionState = MutableSharedFlow<FinderAction>()
    val actionState = _actionState.asSharedFlow()

    private val _uiState = MutableStateFlow<FinderUIState>(FinderUIState.Default)
    val uiState = _uiState.asStateFlow()

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
        if (pathList.isEmpty())
            return

        _uiState.value = FinderUIState.Scanning

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                pathList.forEach { path ->
                    val dirFile = File(path)
                    analyse(dirFile)
                }
            }

            if (duplicateFiles.isEmpty()) {
                _uiState.value = FinderUIState.DuplicateFilesIsEmpty
                return@launch
            }

            _uiState.value = FinderUIState.Default
            viewModelScope.launch {
                _actionState.emit(FinderAction.NavToResult(duplicateFiles))
            }
        }
    }

    private val checkMap = hashMapOf<String, String>()
    private val duplicateFiles = mutableListOf<DuplicateFile>()

    private fun analyse(file: File) {
        if (file.isDirectory) {
            file.listFiles().orEmpty().forEach { resFile ->
                analyse(resFile)
            }
        }
        if (file.isFile) {
            val hashStr = file.hashStr()
            if (checkMap.contains(hashStr)) {
                duplicateFiles.add(DuplicateFile(checkMap[hashStr].orEmpty(), file.absolutePath))
            } else {
                checkMap[hashStr] = file.absolutePath
            }
        }
    }
}