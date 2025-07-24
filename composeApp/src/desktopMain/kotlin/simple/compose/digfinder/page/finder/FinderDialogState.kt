package simple.compose.digfinder.page.finder

import simple.compose.digfinder.data.DuplicateFile
import java.io.File

sealed interface FinderDialogState {

    data object None : FinderDialogState

    data class Result(val duplicateFiles: List<DuplicateFile>) : FinderDialogState

    data object Empty : FinderDialogState

    data class ShowNewFileDialog(val targetDirFile: File, val dropFile: File) : FinderDialogState

    data class ShowFileExistsDialog(val file: DuplicateFile.File) : FinderDialogState
}