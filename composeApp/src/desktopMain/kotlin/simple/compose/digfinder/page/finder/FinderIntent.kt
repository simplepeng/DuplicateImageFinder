package simple.compose.digfinder.page.finder

import simple.compose.digfinder.base.BaseIntent
import simple.compose.digfinder.data.PathWrapper
import java.io.File

sealed interface FinderIntent : BaseIntent {

   data class UpdatePath(val path: String) : FinderIntent

   data class GetProject(val id: Long) : FinderIntent

   data class AddPath(val path: String) : FinderIntent

   data class Scan(val pathList: List<PathWrapper>) : FinderIntent

   data class Watching(val pathList: List<PathWrapper>) : FinderIntent

   data class UpdateChecked(val index: Int, val isChecked: Boolean) : FinderIntent

   data class CheckDropFile(val targetDir: String, val dropFile: File) : FinderIntent

}