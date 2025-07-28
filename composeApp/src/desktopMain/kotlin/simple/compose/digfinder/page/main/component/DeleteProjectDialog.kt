package simple.compose.digfinder.page.main.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteProjectDialog(
   projectName: String,
   onDismissRequest: () -> Unit,
   onConfirm: () -> Unit,
) {
   AlertDialog(
      onDismissRequest = onDismissRequest,
      title = {
         Text("Oops! Delete $projectName?")
      },
      text = {
         Text("Are you sure you want to delete this project?")
      },
      dismissButton = {
         TextButton(onClick = {
            onDismissRequest.invoke()
         }) {
            Text("Cancel")
         }
      },
      confirmButton = {
         TextButton(onClick = {
            onConfirm.invoke()
         }) {
            Text("Confirm")
         }
      }
   )
}