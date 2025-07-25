package simple.compose.digfinder.page.main.dialog

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import simple.compose.digfinder.config.Dimen
import simple.compose.digfinder.theme.AppTheme
import simple.compose.digfinder.widget.AppButton
import simple.compose.digfinder.widget.DialogCard

@Composable
fun AddProjectDialog(
  onDismissRequest: () -> Unit,
  onConfirm: (projectName: String, projectPath: String) -> Unit,
) {
  Dialog(
    properties = DialogProperties(),
    onDismissRequest = onDismissRequest,
  ) {
    DialogContent(onDismissRequest, onConfirm)
  }
}

@Composable
private fun DialogContent(
  onDismissRequest: () -> Unit,
  onConfirm: (projectName: String, projectPath: String) -> Unit,
) {
  var projectName by remember { mutableStateOf("") }
  var projectPath by remember { mutableStateOf("") }

  DialogCard {
    Column(
      modifier = Modifier.fillMaxWidth().padding(Dimen.dialogPadding),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "Add Project",
        style = AppTheme.dialogTitle,
      )
      OutlinedTextField(
        projectName,
        onValueChange = {
          projectName = it
        },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        label = {
          Text(
            text = "Project Name",
          )
        }
      )
      OutlinedTextField(
        projectPath,
        onValueChange = {
          projectPath = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
          Text(text = "Project Path - Option")
        },
      )
      Row(
        modifier = Modifier.align(Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AppButton(onClick = {
          onDismissRequest.invoke()
        }) {
          Text(
            text = "Cancel"
          )
        }
        AppButton(onClick = {
          onConfirm.invoke(projectName, projectPath)
        }) {
          Text(
            text = "Confirm"
          )
        }
      }
    }
  }
}