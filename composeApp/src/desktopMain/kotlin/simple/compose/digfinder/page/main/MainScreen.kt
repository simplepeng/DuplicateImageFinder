package simple.compose.digfinder.page.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import database.Project
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.ic_delete
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import simple.compose.digfinder.page.main.component.AddProjectDialog
import simple.compose.digfinder.page.main.component.DeleteProjectDialog
import simple.compose.digfinder.widget.AppCard
import simple.compose.digfinder.widget.LoadingIndicator

@Composable
fun MainScreen(
   onNavigation: (MainNavigation) -> Unit,
   viewModel: MainViewModel = viewModel { MainViewModel() }
) {
   LaunchedEffect(viewModel.navigationState) {
      viewModel.navigationState.collectLatest {
         onNavigation(it)
      }
   }
   LaunchedEffect(Unit) {
      viewModel.performIntent(MainIntent.GetList)
   }

   val uiState by viewModel.uiState.collectAsState()
   val dialogState by viewModel.dialogState.collectAsState()

   Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center,
   ) {
      ScreenContent(viewModel)

      when (uiState) {
         MainUIState.Loading -> LoadingIndicator()
         MainUIState.Content -> {}
      }
   }

   when (dialogState) {
      MainDialogState.None -> {}

      MainDialogState.AddProject -> {
         AddProjectDialog(
            onDismissRequest = {
               viewModel.updateDialogState(MainDialogState.None)
            },
            onConfirm = { projectName, projectPath ->
               viewModel.performIntent(MainIntent.AddProject(projectName, projectPath))
            }
         )
      }

      is MainDialogState.DeleteProject -> {
         val project = (dialogState as MainDialogState.DeleteProject).project
         DeleteProjectDialog(
            projectName = project.name,
            onDismissRequest = {
               viewModel.updateDialogState(MainDialogState.None)
            },
            onConfirm = {
               viewModel.updateDialogState(MainDialogState.None)
               viewModel.performIntent(MainIntent.DeleteProject(project.id))
            }
         )
      }
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
            Item(
               item = project,
               onItemClick = { item ->
                  viewModel.performNavigation(MainNavigation.ToFinder(item))
               },
               onDeleteClick = { item ->
                  viewModel.updateDialogState(MainDialogState.DeleteProject(item))
               }
            )
         }
      }

      ExtendedFloatingActionButton(
         modifier = Modifier.align(Alignment.BottomEnd)
            .padding(bottom = 20.dp, end = 20.dp),
         onClick = {
            viewModel.updateDialogState(MainDialogState.AddProject)
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
   onItemClick: (Project) -> Unit = {},
   onDeleteClick: (Project) -> Unit = {},
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
         Icon(
            painter = painterResource(Res.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
               .size(25.dp)
               .clickable {
                  onDeleteClick.invoke(item)
               }
               .padding(3.dp)
         )
      }
   }
}