package simple.compose.digfinder.finder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.ic_clear
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import simple.compose.digfinder.data.PathWrapper
import simple.compose.digfinder.dialog.FileExistsDialog
import simple.compose.digfinder.dialog.NewFileDialog
import simple.compose.digfinder.dialog.NoDuplicateFilesDialog
import simple.compose.digfinder.dialog.WatchingDialog
import simple.compose.digfinder.result.ResultDialog
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.AppCard
import simple.compose.digfinder.widget.Content
import java.io.File
import java.net.URI

@Composable
fun FinderScreen(
    viewModel: FinderViewModel = viewModel { FinderViewModel() },
    onAction: (FinderAction) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.actionState.collectLatest {
            onAction(it)
        }
    }

    FinderScreenContent(viewModel)
}

@Composable
fun FinderScreenContent(viewModel: FinderViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/BabyCarer/app/src/main/res/drawable-xxhdpi") }
//    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/Calendar/phone/src/main/res/drawable-xxxhdpi") }
//    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/mooda/app/src/main/res/drawable-xxxhdpi") }
    val pathList by viewModel.pathList.collectAsState()

    var showEmptyDialog by remember(uiState) { mutableStateOf(uiState == FinderUIState.DuplicateFilesIsEmpty) }
    var showResultDialog by remember(uiState) { mutableStateOf(uiState is FinderUIState.ShowResultDialog) }
    var showWatchingDialog by remember(uiState) { mutableStateOf(uiState == FinderUIState.Watching) }

    Content(
        onBack = {
            viewModel.doAction(FinderAction.Back)
        },
        showLoading = uiState is FinderUIState.Scanning
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = pathField,
                onValueChange = {
                    pathField = it
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
                            pathField = ""
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
                    viewModel.performIntent(FinderIntent.AddPath(pathField))
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
                AppButton(onClick = {
                    viewModel.performIntent(FinderIntent.Watching(pathList))
                }) {
                    Text(
                        text = "watching"
                    )
                }
            }
            //
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                itemsIndexed(items = pathList, key = { index, item -> item.path }) { index, item ->
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

    if (showEmptyDialog) {
        NoDuplicateFilesDialog(
            onDismissRequest = {
                showEmptyDialog = false
            }
        )
    }

    if (showResultDialog) {
        ResultDialog(
            onDismissRequest = {
                showResultDialog = false
            },
            duplicateFiles = (uiState as FinderUIState.ShowResultDialog).duplicateFiles
        )
    }

    if (showWatchingDialog) {
        WatchingDialog(onDismissRequest = {
            showWatchingDialog = false
        })
    }

    when (uiState) {
        is FinderUIState.ShowNewFileDialog -> {
            NewFileDialog(
                onDismissRequest = {
                    viewModel.updateUIState(FinderUIState.Default)
                }
            )
        }

        is FinderUIState.ShowFileExistsDialog -> {
            FileExistsDialog(
                (uiState as FinderUIState.ShowFileExistsDialog).file,
                onDismissRequest = {
                    viewModel.updateUIState(FinderUIState.Default)
                })
        }

        else -> {

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
                                onDropFile.invoke(item.path, it)
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
                        color = MaterialTheme.colors.primary,
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
                text = item.path,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = {
                    onCheckedChange.invoke(it)
                }
            )
        }
    }
}