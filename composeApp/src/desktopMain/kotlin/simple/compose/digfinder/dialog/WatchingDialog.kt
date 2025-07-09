package simple.compose.digfinder.dialog

import KottieAnimation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import duplicateimagefinder.composeapp.generated.resources.Res
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import simple.compose.digfinder.ext.debugBackground
import simple.compose.digfinder.widget.AppButton
import utils.KottieConstants

@Composable
fun WatchingDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        DialogContent()
    }
}

@Composable
private fun DialogContent() {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/watching.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation) // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
//        isPlaying = true,
//        restartOnPlay = false,
        iterations = KottieConstants.IterateForever
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.debugBackground()
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            KottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = Modifier.size(300.dp)
            )
            Text(
                text = "Watching"
            )
        }
        AppButton(onClick = {

        }) {
            Text(
                text = "Cancel"
            )
        }
    }
}