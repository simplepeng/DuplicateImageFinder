package simple.compose.digfinder.page.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import database.Project
import kotlinx.coroutines.flow.collectLatest
import simple.compose.digfinder.dialog.AddProjectDialog
import simple.compose.digfinder.widget.AppCard

@Composable
fun MainScreen(
    onAction: (MainAction) -> Unit,
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    LaunchedEffect(viewModel.actionState) {
        viewModel.actionState.collectLatest {
            onAction(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getList()
    }

    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ScreenContent(viewModel)

        when (uiState) {
            MainUIState.Content -> {}
            MainUIState.Loading -> {}
        }
    }

    when (dialogState) {
        MainDialogState.AddProjectDialog -> {
            AddProjectDialog(
                onDismissRequest = {
                    viewModel.updateDialogState(MainDialogState.None)
                },
                onConfirm = { projectName, projectPath ->
                    viewModel.updateDialogState(MainDialogState.None)
                    viewModel.addProject(projectName, projectPath)
                }
            )
        }

        MainDialogState.None -> {}
    }
}

@Composable
private fun ScreenContent(
    viewModel: MainViewModel
) {
    val projectList by viewModel.projectList.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = projectList,
                key = { it.id }) { project ->
                Item(item = project, onItemClick = { item ->
                    viewModel.doAction(MainAction.NavToFinder(item))
                })
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp),
            onClick = {
                viewModel.updateDialogState(MainDialogState.AddProjectDialog)
            },
        ) {
            Text(
                text = "Add"
            )
        }
    }
}

@Composable
private fun Item(
    item: Project,
    onItemClick: (Project) -> Unit = {}
) {
    AppCard(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onItemClick(item)
            },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = item.name
            )
        }
    }
}