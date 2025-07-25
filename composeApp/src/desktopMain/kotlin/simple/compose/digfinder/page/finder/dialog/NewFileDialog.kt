package simple.compose.digfinder.page.finder.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import simple.compose.digfinder.config.Dimen
import simple.compose.digfinder.theme.AppTheme
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.DialogCard
import java.io.File

@Composable
fun NewFileDialog(
    dropFile: File,
    onDismissRequest: () -> Unit,
    onSure: (newFileName: String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
        )
    ) {
        DialogContent(dropFile, onDismissRequest, onSure)
    }
}

@Composable
private fun DialogContent(
    dropFile: File,
    onDismissRequest: () -> Unit,
    onSure: (newFileName: String) -> Unit = {}
) {
    var newFileNameState by remember { mutableStateOf(dropFile.nameWithoutExtension) }

    DialogCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(Dimen.dialogPadding)
        ) {
            Text(
                text = "Move File To Target Dir",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = AppTheme.dialogTitle
            )
            AsyncImage(
                model = dropFile,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit,
            )
            OutlinedTextField(
                newFileNameState,
                onValueChange = {
                    newFileNameState = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "File Name")
                },
                textStyle = AppTheme.textFiled
            )
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AppButton(onClick = {
                    onDismissRequest.invoke()
                }) {
                    Text(text = "Cancel")
                }
                AppButton(onClick = {
                    onSure.invoke("$newFileNameState.${dropFile.extension}")
                }) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}