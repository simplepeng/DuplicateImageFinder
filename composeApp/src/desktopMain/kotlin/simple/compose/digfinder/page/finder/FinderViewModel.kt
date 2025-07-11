package simple.compose.digfinder.page.finder

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
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent

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

    fun updateUIState(state: FinderUIState) {
        _uiState.value = state
    }

    fun performIntent(intent: FinderIntent) {
        when (intent) {
            is FinderIntent.AddPath -> addPath(intent.path)
            is FinderIntent.Scan -> scan(intent.pathList)
            is FinderIntent.UpdateChecked -> updateChecked(intent.index, intent.isChecked)
            is FinderIntent.Watching -> _uiState.value = FinderUIState.Watching
            is FinderIntent.CheckDropFile -> checkDropFile(intent.targetDir, intent.dropFile)
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

        hashStrMap.clear()
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

            _uiState.value = FinderUIState.ShowResultDialog(duplicateFiles.toList())
        }
    }

    private val hashStrMap = hashMapOf<String, DuplicateFile.File>()
    private val duplicateFiles = hashSetOf<DuplicateFile>()

    private fun checkHashMap() {
        if (hashStrMap.isNotEmpty()) return

        val checkedList = _pathList.value.filter { it.isChecked }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                checkedList.forEach { wrapper ->
                    val dirFile = File(wrapper.path)
                    analyse(dirFile)
                }
            }
        }
    }

    private suspend fun analyse(file: File) {
        if (file.isDirectory) {
            file.listFiles().orEmpty().forEach { resFile ->
                analyse(resFile)
            }
        }
        if (file.isFile) {
            val hashStr = file.hashStr()
            if (hashStrMap.contains(hashStr)) {
                duplicateFiles.add(
                    DuplicateFile(
                        hashStrMap[hashStr]!!,
                        DuplicateFile.File(
                            path = file.absolutePath,
                            name = file.name,
                            size = file.length()
                        )
                    )
                )
            } else {
                hashStrMap[hashStr] = DuplicateFile.File(
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

    private fun watching(pathList: List<PathWrapper>) {
        if (pathList.isEmpty()) {
            return
        }

        _uiState.value = FinderUIState.Watching

        pathList.forEach { wrapper ->
            val dirFile = File(wrapper.path)
            if (dirFile.isDirectory) {
                val directory = Paths.get(wrapper.path)
                val watchService = FileSystems.getDefault().newWatchService()
                directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)

                println("开始监听目录: $directory")

                while (true) {
                    val key = watchService.take() // 阻塞等待事件

                    for (event in key.pollEvents()) {
                        val kind: WatchEvent.Kind<*>? = event.kind()
                        val fileName = event.context() as Path?

                        if (kind === StandardWatchEventKinds.ENTRY_CREATE) {
                            println("文件创建: $fileName")
                        }
                    }

                    // 重置key，继续监听
                    val valid = key.reset()
                    if (!valid) {
                        break
                    }
                }
            }
        }
    }

    private fun checkDropFile(
        targetDir: String,
        dropFile: File
    ) {
        val targetDirFile = File(targetDir)
        if (!targetDirFile.exists() || !targetDirFile.isDirectory) {
            return
        }
        if (!dropFile.exists() || !dropFile.isFile) {
            return
        }

        checkHashMap()

        val hashStr = dropFile.hashStr()
        if (hashStrMap.contains(hashStr)) {
            println("oops，已经有这个资源文件了！")
            hashStrMap.get(hashStr)?.let {
                _uiState.value = FinderUIState.ShowFileExistsDialog(it)
            }
        } else {
            println("这个文件是新增的哦，是否重命名呢？")
            _uiState.value = FinderUIState.ShowNewFileDialog(targetDirFile, dropFile)
        }
    }

    fun copyFileToTargetDir(
        targetDirFile: File,
        dropFile: File,
        newFileName: String,
    ) {
        if (!targetDirFile.exists() || !targetDirFile.isDirectory) {
            return
        }
        if (!dropFile.exists() || !dropFile.isFile) {
            return
        }
        val newFile = File(targetDirFile, newFileName)
        dropFile.copyTo(newFile)
        println("拷贝文件成功！")
    }
}