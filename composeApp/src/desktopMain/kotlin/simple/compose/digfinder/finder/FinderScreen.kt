package simple.compose.digfinder.finder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.AppCard
import simple.compose.digfinder.widget.Content

@Composable
fun FinderScreen(
    viewModel: FinderViewModel = viewModel { FinderViewModel() },
    onAction: (FinderAction) -> Unit,
) {
    LaunchedEffect(Unit){
        viewModel.actionState.collectLatest {
            onAction(it)
        }
    }

    FinderScreenContent(viewModel)
}

@Composable
fun FinderScreenContent(viewModel: FinderViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var pathField by remember { mutableStateOf("") }
    val pathList by viewModel.pathList.collectAsState()

    Content(
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
                items(items = pathList) {
                    AppCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}