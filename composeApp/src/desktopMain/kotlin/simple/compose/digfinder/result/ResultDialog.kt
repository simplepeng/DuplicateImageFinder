package simple.compose.digfinder.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.ext.debugBackground
import simple.compose.digfinder.ext.formatStr

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
        modifier = Modifier.padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
        ) {
            items(duplicateFiles) { item ->
                RowItem(item)
            }
        }
    }
}

@Composable
private fun RowItem(item: DuplicateFile) {
    OutlinedCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Item(item.file1)
            Item(item.file2)
        }
    }
}

@Composable
private fun RowScope.Item(file: DuplicateFile.File) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
            .clickable {

            }.padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        AsyncImage(
            model = file.path,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = file.name
        )
        Text(
            text = file.size.formatStr
        )
    }
}