package simple.compose.digfinder.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import simple.compose.digfinder.dialog.AddProjectDialog

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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

    val uiState by viewModel.uiState.collectAsState()

    ScreenContent(viewModel)

    when (uiState) {
        MainUIState.AddProjectDialog -> {
            AddProjectDialog(
                onDismissRequest = {
                    viewModel.updateUIState(MainUIState.Content)
                }
            )
        }

        MainUIState.Content -> TODO()
        MainUIState.Loading -> ContainedLoadingIndicator()
    }
}

@Composable
private fun ScreenContent(
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp, end = 20.dp)
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                viewModel.updateUIState(MainUIState.AddProjectDialog)
            },
        ) {
            Text(
                text = "Add"
            )
        }
    }
}