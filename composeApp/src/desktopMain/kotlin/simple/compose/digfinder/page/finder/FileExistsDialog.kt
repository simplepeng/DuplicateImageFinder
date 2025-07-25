package simple.compose.digfinder.page.finder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import simple.compose.digfinder.config.Dimen
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.DialogCard

@Composable
fun FileExistsDialog(
    file: DuplicateFile.File,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
        )
    ) {
        DialogContent(file, onDismissRequest)
    }
}

@Composable
private fun DialogContent(
    file: DuplicateFile.File,
    onDismissRequest: () -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current

    DialogCard {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Dimen.dialogPadding),
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
            AppButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(file.name.substringBefore(".")))
                    onDismissRequest.invoke()
                }
            ) {
                Text(text = "Copy Name")
            }
        }
    }
}