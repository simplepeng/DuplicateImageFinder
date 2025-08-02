package simple.compose.digfinder.page.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.name_copied
import duplicateimagefinder.composeapp.generated.resources.optimized_result_text
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.ext.formatStr
import simple.compose.digfinder.widget.AppCard

@Composable
fun ResultDialog(
   onDismissRequest: () -> Unit,
   duplicateFiles: List<DuplicateFile>
) {
   Dialog(
      onDismissRequest = onDismissRequest
   ) {
      Content(duplicateFiles)
   }
}

@Composable
private fun Content(
   duplicateFiles: List<DuplicateFile>
) {
   val totalSize = remember { duplicateFiles.sumOf { it.file2.size } }
   val coroutineScope = rememberCoroutineScope()
   val snackBarHostState = remember { SnackbarHostState() }
//    val clipboard = LocalClipboard.current
   val clipboardManager = LocalClipboardManager.current

   Card(
      modifier = Modifier.padding(vertical = 10.dp),
      shape = RoundedCornerShape(10.dp)
   ) {
      Box(
         modifier = Modifier.fillMaxSize()
      ) {
         Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
               .padding(10.dp),
         ) {
            Text(
               text = stringResource(Res.string.optimized_result_text, totalSize.formatStr)
            )
            LazyColumn(
               verticalArrangement = Arrangement.spacedBy(5.dp),
               modifier = Modifier.fillMaxSize()

            ) {
               items(duplicateFiles) { item ->
                  RowItem(item, onItemClick = { file ->
                     coroutineScope.launch {
                        val name = file.name.substringBeforeLast(".")
                        clipboardManager.setText(AnnotatedString(name))
//                                clipboard.setClipEntry(ClipEntry(file.name))
                        snackBarHostState.showSnackbar(getString(Res.string.name_copied, file.name))
                     }
                  })
               }
            }
         }
         SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
         )
      }
   }
}

@Composable
private fun RowItem(
   item: DuplicateFile,
   onItemClick: (DuplicateFile.File) -> Unit = {}
) {
   AppCard {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.spacedBy(5.dp),
      ) {
         Item(item.file1, onItemClick)
         Item(item.file2, onItemClick)
      }
   }
}

@Composable
private fun RowScope.Item(
   file: DuplicateFile.File,
   onItemClick: (DuplicateFile.File) -> Unit = {}
) {
   Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f)
         .clickable {
            onItemClick.invoke(file)
         }.padding(vertical = 5.dp, horizontal = 10.dp)
   ) {
      AsyncImage(
         model = file.path,
         contentDescription = null,
         modifier = Modifier.fillMaxWidth()
      )
      Text(
         text = file.name
      )
      Text(
         text = file.size.formatStr
      )
   }

}