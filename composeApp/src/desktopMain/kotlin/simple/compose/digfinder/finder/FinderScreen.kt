package simple.compose.digfinder.finder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.ic_clear
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import simple.compose.digfinder.dialog.DuplicateFilesEmptyDialog
import simple.compose.digfinder.result.ResultDialog
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.AppCard
import simple.compose.digfinder.widget.Content

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
//    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/BabyCarer/app/src/main/res/drawable-xxhdpi") }
//    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/Calendar/phone/src/main/res/drawable-xxhdpi") }
    var pathField by remember { mutableStateOf("/Users/simple/Desktop/worksapce/android/mooda/app/src/main/res/drawable-xxxhdpi") }
    val pathList by viewModel.pathList.collectAsState()

    var showEmptyDialog by remember(uiState) { mutableStateOf(uiState == FinderUIState.DuplicateFilesIsEmpty) }
    var showResultDialog by remember(uiState) { mutableStateOf(uiState is FinderUIState.ShowResultDialog) }

    Content(
        onBack = {
            viewModel.doAction(FinderAction.Back)
        },
        showLoading = uiState is FinderUIState.Scanning
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp),
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
            }
            //
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                itemsIndexed(items = pathList, key = { index, item -> item.path }) { index, item ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
                        ) {
                            Text(
                                text = item.path,
                                modifier = Modifier
                            )
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = {
                                    viewModel.performIntent(FinderIntent.UpdateChecked(index, it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showEmptyDialog) {
        DuplicateFilesEmptyDialog(
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
}