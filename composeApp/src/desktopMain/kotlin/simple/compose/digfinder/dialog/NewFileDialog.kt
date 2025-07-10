package simple.compose.digfinder.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import simple.compose.digfinder.config.Dimens
import simple.compose.digfinder.widget.DialogCard
import java.io.File

@Composable
fun NewFileDialog(
    dropFile: File,
    onDismissRequest: () -> Unit,
    onSure: (newFileName: String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        DialogContent(dropFile, onSure)
    }
}

@Composable
private fun DialogContent(
    dropFile: File,
    onSure: (newFileName: String) -> Unit = {}
) {
    var newFileNameState by remember { mutableStateOf(dropFile.nameWithoutExtension) }

    DialogCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(Dimens.dialogPadding)
        ) {
            AsyncImage(
                model = dropFile,
                contentDescription = null,
            )
            OutlinedTextField(
                newFileNameState,
                onValueChange = {
                    newFileNameState = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                onSure.invoke("$newFileNameState.${dropFile.extension}")
            }) {
                Text(text = "Sure")
            }
        }
    }
}