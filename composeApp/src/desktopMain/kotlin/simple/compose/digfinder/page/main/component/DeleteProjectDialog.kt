package simple.compose.digfinder.page.main.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.cancel
import duplicateimagefinder.composeapp.generated.resources.confirm
import duplicateimagefinder.composeapp.generated.resources.delete_project_desc
import duplicateimagefinder.composeapp.generated.resources.delete_project_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteProjectDialog(
   projectName: String,
   onDismissRequest: () -> Unit,
   onConfirm: () -> Unit,
) {
   AlertDialog(
      onDismissRequest = onDismissRequest,
      title = {
         Text(text = stringResource(Res.string.delete_project_title, projectName))
      },
      text = {
         Text(text = stringResource(Res.string.delete_project_desc))
      },
      dismissButton = {
         TextButton(onClick = {
            onDismissRequest.invoke()
         }) {
            Text(stringResource(Res.string.cancel))
         }
      },
      confirmButton = {
         TextButton(onClick = {
            onConfirm.invoke()
         }) {
            Text(stringResource(Res.string.confirm))
         }
      }
   )
}