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
import simple.compose.digfinder.widget.DialogCard

@Composable
fun NewFileDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        DialogContent()
    }
}

@Composable
private fun DialogContent() {
    var newFileNameState by remember { mutableStateOf("") }

    DialogCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            OutlinedTextField(
                newFileNameState,
                onValueChange = {
                    newFileNameState = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {

            }) {
                Text(text = "Sure")
            }
        }
    }
}