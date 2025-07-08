package simple.compose.digfinder.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.widget.Content

@Composable
fun ResultScreen(
    duplicateFiles: List<DuplicateFile>
) {
    ResultScreenContent(duplicateFiles)
}

@Composable
fun ResultScreenContent(
    duplicateFiles: List<DuplicateFile>
) {
    Content {
        LazyColumn {
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

    }
}