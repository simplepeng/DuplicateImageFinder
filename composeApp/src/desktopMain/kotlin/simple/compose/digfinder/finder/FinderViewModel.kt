package simple.compose.digfinder.finder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.data.PathWrapper
import simple.compose.digfinder.ext.hashStr
import java.io.File
import kotlin.collections.forEach
import kotlin.collections.orEmpty

class FinderViewModel : ViewModel() {

    private val _actionState = MutableSharedFlow<FinderAction>()
    val actionState = _actionState.asSharedFlow()

    fun doAction(action: FinderAction) {
        viewModelScope.launch {
            _actionState.emit(action)
        }
    }

    private val _uiState = MutableStateFlow<FinderUIState>(FinderUIState.Default)
    val uiState = _uiState.asStateFlow()

    fun performIntent(intent: FinderIntent) {
        when (intent) {
            is FinderIntent.AddPath -> addPath(intent.path)
            is FinderIntent.Scan -> scan(intent.pathList)
            is FinderIntent.UpdateChecked -> updateChecked(intent.index, intent.isChecked)
        }
    }

    private val _pathList = MutableStateFlow<List<PathWrapper>>(emptyList())
    val pathList = _pathList.asStateFlow()

    private fun addPath(path: String) {
        if (path.isEmpty()) {
            return
        }
        if (_pathList.value.map { it.path }.contains(path)) {
            return
        }
        _pathList.value += PathWrapper(path)
    }

    private fun scan(pathList: List<PathWrapper>) {
        val checkedList = pathList.filter { it.isChecked }

        if (checkedList.isEmpty())
            return

        checkMap.clear()
        duplicateFiles.clear()
        _uiState.value = FinderUIState.Scanning

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                checkedList.forEach { wrapper ->
                    val dirFile = File(wrapper.path)
                    analyse(dirFile)
                }
            }

            if (duplicateFiles.isEmpty()) {
                _uiState.value = FinderUIState.DuplicateFilesIsEmpty
                return@launch
            }

            _uiState.value = FinderUIState.ShowResultDialog(duplicateFiles)
        }
    }

    private val checkMap = hashMapOf<String, DuplicateFile.File>()
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
                duplicateFiles.add(
                    DuplicateFile(
                        checkMap[hashStr]!!,
                        DuplicateFile.File(
                            path = file.absolutePath,
                            name = file.name,
                            size = file.length()
                        )
                    )
                )
            } else {
                checkMap[hashStr] = DuplicateFile.File(
                    path = file.absolutePath,
                    name = file.name,
                    size = file.length()
                )
            }
        }
    }

    private fun updateChecked(index: Int, isChecked: Boolean) {
        _pathList.update {
            it.toMutableList().apply {
                this[index] = this[index].copy(isChecked = isChecked)
            }
        }
    }
}