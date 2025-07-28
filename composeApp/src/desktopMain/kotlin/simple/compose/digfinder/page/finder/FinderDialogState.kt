package simple.compose.digfinder.page.finder

import simple.compose.digfinder.base.BaseDialogState
import simple.compose.digfinder.data.DuplicateFile
import java.io.File

sealed interface FinderDialogState : BaseDialogState {

   data object None : FinderDialogState

   data class Result(val duplicateFiles: List<DuplicateFile>) : FinderDialogState

   data object Empty : FinderDialogState

   data class NewFile(val targetDirFile: File, val dropFile: File) : FinderDialogState

   data class FileExists(val file: DuplicateFile.File) : FinderDialogState
}