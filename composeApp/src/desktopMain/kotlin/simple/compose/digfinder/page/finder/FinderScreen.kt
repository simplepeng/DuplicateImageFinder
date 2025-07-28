package simple.compose.digfinder.page.finder

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.dragData
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import database.Project
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.ic_clear
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import simple.compose.digfinder.data.PathWrapper
import simple.compose.digfinder.page.finder.component.FileExistsDialog
import simple.compose.digfinder.page.finder.component.NewFileDialog
import simple.compose.digfinder.page.finder.component.NoDuplicateFilesDialog
import simple.compose.digfinder.page.result.ResultDialog
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.AppCard
import simple.compose.digfinder.widget.Content
import simple.compose.digfinder.widget.LoadingIndicator
import java.io.File
import java.net.URI

@Composable
fun FinderScreen(
   projectId: Long,
   viewModel: FinderViewModel = viewModel { FinderViewModel() },
   onAction: (FinderNavigation) -> Unit,
) {
   LaunchedEffect(Unit) {
      viewModel.actionState.collectLatest {
         onAction(it)
      }
   }
   LaunchedEffect(projectId) {
      viewModel.performIntent(FinderIntent.GetProject(projectId))
   }

   val uiState by viewModel.uiState.collectAsState()
   val project by viewModel.project.collectAsState()

   Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
   ) {
      when (uiState) {
         FinderUIState.Loading -> LoadingIndicator()
         else -> {}
      }

      ScreenContent(project, viewModel)
   }

   val dialogState by viewModel.dialogState.collectAsState()
   when (dialogState) {
      is FinderDialogState.Result -> {
         ResultDialog(
            onDismissRequest = {
               viewModel.updateDialogState(FinderDialogState.None)
            },
            duplicateFiles = (dialogState as FinderDialogState.Result).duplicateFiles
         )
      }

      FinderDialogState.Empty -> {
         NoDuplicateFilesDialog(
            onDismissRequest = {
               viewModel.updateDialogState(FinderDialogState.None)
            }
         )
      }

      is FinderDialogState.ShowNewFileDialog -> {
         val state = (dialogState as FinderDialogState.ShowNewFileDialog)
         NewFileDialog(
            state.dropFile,
            onDismissRequest = {
               viewModel.updateDialogState(FinderDialogState.None)
            },
            onSure = { newFileName ->
               viewModel.copyFileToTargetDir(state.targetDirFile, state.dropFile, newFileName)
               viewModel.updateDialogState(FinderDialogState.None)
            }
         )
      }

      is FinderDialogState.ShowFileExistsDialog -> {
         FileExistsDialog(
            (dialogState as FinderDialogState.ShowFileExistsDialog).file,
            onDismissRequest = {
               viewModel.updateDialogState(FinderDialogState.None)
            })
      }

      else -> {}
   }
}

@Composable
fun ScreenContent(
   project: Project?,
   viewModel: FinderViewModel
) {
   val uiState by viewModel.uiState.collectAsState()
   var path by remember { mutableStateOf("") }
   val pathList by viewModel.pathList.collectAsState()

   Content(
      onBack = {
         viewModel.doAction(FinderNavigation.Back)
      },
      title = {
         Text(
            text = project?.name.orEmpty()
         )
      },
      showLoading = uiState is FinderUIState.Scanning
   ) {
      Column(
         modifier = Modifier.fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 10.dp),
         verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
         OutlinedTextField(
            value = path,
            onValueChange = {
               path = it
            },
            label = {
               Text(
                  text = "path"
               )
            },
            trailingIcon = {
               Icon(
                  painter = painterResource(Res.drawable.ic_clear),
                  contentDescription = null,
                  modifier = Modifier.clickable {
                     path = ""
                  }.padding(5.dp)
               )
            },
            modifier = Modifier.fillMaxWidth()
         )
         //
         Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
         ) {
            AppButton(onClick = {
               viewModel.performIntent(FinderIntent.AddPath(path))
            }) {
               Text(
                  text = "add"
               )
            }
            AppButton(onClick = {
               viewModel.performIntent(FinderIntent.Scan(pathList))
            }) {
               Text(
                  text = "scan"
               )
            }
//                AppButton(onClick = {
//                    viewModel.performIntent(FinderIntent.Watching(pathList))
//                }) {
//                    Text(
//                        text = "watching"
//                    )
//                }
         }
         //
         LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth().weight(1f)
         ) {
            itemsIndexed(
               items = pathList,
//               key = { index, item -> item.projectDirs.id }
            ) { index, item ->
               PathItem(
                  item,
                  onCheckedChange = {
                     viewModel.performIntent(FinderIntent.UpdateChecked(index, it))
                  },
                  onDropFile = { targetDir, dropFile ->
                     viewModel.performIntent(FinderIntent.CheckDropFile(targetDir, dropFile))
                  })
            }
         }
      }
   }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PathItem(
   item: PathWrapper,
   onCheckedChange: (Boolean) -> Unit,
   onDropFile: (targetDir: String, dropFile: File) -> Unit = { _, _ -> },
) {
   var showTargetBorder by remember { mutableStateOf(false) }
   val dragAndDropTarget = remember {
      object : DragAndDropTarget {

         override fun onDrop(event: DragAndDropEvent): Boolean {
            println("Action at the target: ${event.action}")
            val dragData = event.dragData()
            if (dragData is DragData.FilesList) {
               dragData.readFiles().forEach { path ->
                  println(path)
                  File(URI(path)).also {
                     if (it.isFile && it.exists()) {
                        println(it.absolutePath)
                        onDropFile.invoke(item.projectDirs.dirPath, it)
                     }
                  }
               }
            }
            return true
         }

         override fun onEntered(event: DragAndDropEvent) {
            showTargetBorder = true
         }

         override fun onExited(event: DragAndDropEvent) {
            showTargetBorder = false
         }

         override fun onEnded(event: DragAndDropEvent) {
            showTargetBorder = false
         }
      }
   }

   AppCard(
      modifier = Modifier.fillMaxWidth()
         .then(
            if (showTargetBorder)
               Modifier.border(
                  width = 1.dp,
                  color = MaterialTheme.colorScheme.primary,
                  shape = RoundedCornerShape(15.dp)
               )
            else
               Modifier
         )
         .dragAndDropTarget(
            shouldStartDragAndDrop = { true },
            target = dragAndDropTarget
         )
   ) {
      Row(
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier.fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
      ) {
         Text(
            text = item.projectDirs.dirPath,
            modifier = Modifier.weight(1f)
         )
//            Switch(
//                checked = item.isChecked,
//                onCheckedChange = onCheckedChange
//            )
         Checkbox(
            checked = item.isChecked,
            onCheckedChange = {
               onCheckedChange.invoke(it)
            }
         )
      }
   }
}