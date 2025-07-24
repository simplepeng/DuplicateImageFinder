package simple.compose.digfinder.page.finder

import simple.compose.digfinder.base.BaseUIState
import simple.compose.digfinder.data.DuplicateFile
import java.io.File

sealed interface FinderUIState : BaseUIState {

    data object Default : FinderUIState

    data object Loading : FinderUIState

    data object Content : FinderUIState

    data object Scanning : FinderUIState

    data object DuplicateFilesIsEmpty : FinderUIState

    data class ShowResultDialog(val duplicateFiles: List<DuplicateFile>) : FinderUIState

    data object Watching : FinderUIState

    data class ShowNewFileDialog(val targetDirFile: File, val dropFile: File) : FinderUIState

    data class ShowFileExistsDialog(val file: DuplicateFile.File) : FinderUIState
}