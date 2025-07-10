package simple.compose.digfinder.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.widget.DialogCard

@Composable
fun FileExistsDialog(
    file: DuplicateFile.File,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        DialogContent(file)
    }
}

@Composable
private fun DialogContent(file: DuplicateFile.File,) {
    DialogCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Oops! File is Exists"
            )
            AsyncImage(
                model = file.path,
                contentDescription = null,
            )
            Text(
                text = file.name
            )
            Button(
                onClick = {

                }
            ) {
                Text(text = "Copy Name")
            }
        }
    }
}