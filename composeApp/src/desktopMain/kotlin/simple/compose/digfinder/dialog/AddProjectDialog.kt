package simple.compose.digfinder.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import simple.compose.digfinder.theme.AppTheme
import simple.compose.digfinder.widget.DialogCard

@Composable
fun AddProjectDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismissRequest,
    ) {
        DialogContent()
    }
}

@Composable
private fun DialogContent() {
    var projectName by remember { mutableStateOf("") }

    DialogCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add Project",
                style = AppTheme.dialogTitle
            )
            OutlinedTextField(
                projectName,
                onValueChange = {
                    projectName = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Project Name")
                }
            )
        }
    }
}