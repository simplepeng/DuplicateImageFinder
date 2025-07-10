package simple.compose.digfinder.finder

import simple.compose.digfinder.data.DuplicateFile
import java.io.File

sealed interface FinderUIState {

    data object Default : FinderUIState

    data object Scanning : FinderUIState

    data object DuplicateFilesIsEmpty : FinderUIState

    data class ShowResultDialog(val duplicateFiles: List<DuplicateFile>) : FinderUIState

    data object Watching : FinderUIState

    data class ShowNewFileDialog(val targetDirFile: File, val dropFile: File) : FinderUIState

    data class ShowFileExistsDialog(val file: DuplicateFile.File) : FinderUIState
}