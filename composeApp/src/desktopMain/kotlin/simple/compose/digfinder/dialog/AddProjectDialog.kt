package simple.compose.digfinder.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddProjectDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismissRequest,
    ) {

    }
}