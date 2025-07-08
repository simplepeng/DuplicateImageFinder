package simple.compose.digfinder.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DuplicateFilesEmptyDialog(
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text("Duplicate Files Is Empty")
        },
        buttons = {

        }
    )
}