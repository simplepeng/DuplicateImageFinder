package simple.compose.digfinder.page.finder.component

import KottieAnimation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import duplicateimagefinder.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition

@Composable
fun NoDuplicateFilesDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        NoDuplicateFilesDialogContent(onDismissRequest)
    }
}

@Composable
fun NoDuplicateFilesDialogContent(
    onDismissRequest: () -> Unit,
) {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/no_duplicate_files.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation) // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
//        isPlaying = true,
//        restartOnPlay = false,
//        iterations = KottieConstants.IterateForever
    )
    LaunchedEffect(animationState.isCompleted) {
        if (animationState.isCompleted) {
            delay(200)
            onDismissRequest.invoke()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = Modifier.size(300.dp)
        )
        Text(
            text = "恭喜你！竟然一个重复的资源文件都没有！",
            color = Color.White
        )
    }
}