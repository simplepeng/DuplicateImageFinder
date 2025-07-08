package simple.compose.digfinder.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import simple.compose.digfinder.data.DuplicateFile

@Composable
fun ResultDialog(
    onDismissRequest: () -> Unit,
    duplicateFiles: List<DuplicateFile>
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        ResultScreenContent(duplicateFiles)
    }
}

@Composable
fun ResultScreenContent(
    duplicateFiles: List<DuplicateFile>
) {
    Card(
        modifier = Modifier.padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(duplicateFiles) { item ->
                RowItem(item)
            }
        }
    }
}

@Composable
private fun RowItem(item: DuplicateFile) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Item(item.path1)
        Item(item.path2)
    }
}

@Composable
private fun RowScope.Item(path: String) {
    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AsyncImage(
            model = path,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = path.substringAfterLast("/")
        )
    }
}