package simple.compose.digfinder.page.finder

import androidx.lifecycle.viewModelScope
import database.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import simple.compose.digfinder.base.BaseViewModel
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.data.PathWrapper
import simple.compose.digfinder.db.DbHelper
import simple.compose.digfinder.ext.hashStr
import simple.compose.digfinder.page.main.MainDialogState
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent

class FinderViewModel : BaseViewModel<FinderAction, FinderUIState, FinderIntent>(FinderUIState.Default) {


    override fun performIntent(intent: FinderIntent) {
        when (intent) {
            is FinderIntent.GetProject -> getProject(intent.id)
            is FinderIntent.AddPath -> addPath(intent.path)
            is FinderIntent.Scan -> scan(intent.pathList)
            is FinderIntent.UpdateChecked -> updateChecked(intent.index, intent.isChecked)
            is FinderIntent.Watching -> {}
            is FinderIntent.CheckDropFile -> checkDropFile(intent.targetDir, intent.dropFile)
        }
    }

    private val _dialogState = MutableStateFlow<FinderDialogState>(FinderDialogState.None)
    val dialogState = _dialogState.asStateFlow()

    fun updateDialogState(state: FinderDialogState) {
        _dialogState.value = state
    }

    private val _project = MutableStateFlow<Project?>(null)
    val project = _project.asStateFlow()

    private val _pathList = MutableStateFlow<List<PathWrapper>>(emptyList())
    val pathList = _pathList.asStateFlow()

    private fun getProject(id: Long) {
        updateUIState(FinderUIState.Loading)
        viewModelScope.launch {
            DbHelper.getProject(id).also {
                _project.value = it
            }
            val dirPathList = DbHelper.getDirPathList(id)
            _pathList.value = dirPathList.map { PathWrapper(it) }
            updateUIState(FinderUIState.Content)
        }
    }

    private fun addPath(path: String) {
        if (_project.value == null) {
            return
        }
        if (path.isEmpty()) {
            return
        }
        if (_pathList.value.map { it.projectDirs.dirPath }.contains(path)) {
            return
        }
        viewModelScope.launch {
            DbHelper.addDirPath(_project.value!!.id, path).also {
                _pathList.value += PathWrapper(it)
            }
        }
    }

    private fun scan(pathList: List<PathWrapper>) {
        val checkedList = pathList.filter { it.isChecked }

        if (checkedList.isEmpty())
            return

        hashStrMap.clear()
        duplicateFiles.clear()
        updateUIState(FinderUIState.Scanning)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                checkedList.forEach { wrapper ->
                    val dirFile = File(wrapper.projectDirs.dirPath)
                    analyse(dirFile)
                }
            }

            if (duplicateFiles.isEmpty()) {
                updateUIState(FinderUIState.DuplicateFilesIsEmpty)
                return@launch
            }

            updateUIState(FinderUIState.ShowResultDialog(duplicateFiles.toList()))
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
                    val dirFile = File(wrapper.projectDirs.dirPath)
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

        updateUIState(FinderUIState.Watching)

        pathList.forEach { wrapper ->
            val dirFile = File(wrapper.projectDirs.dirPath)
            if (dirFile.isDirectory) {
                val directory = Paths.get(wrapper.projectDirs.dirPath)
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
                updateUIState(FinderUIState.ShowFileExistsDialog(it))
            }
        } else {
            println("这个文件是新增的哦，是否重命名呢？")
            updateUIState(FinderUIState.ShowNewFileDialog(targetDirFile, dropFile))
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