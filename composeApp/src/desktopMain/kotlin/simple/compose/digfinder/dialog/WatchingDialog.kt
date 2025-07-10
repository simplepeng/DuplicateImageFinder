package simple.compose.digfinder.dialog

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
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
        DialogContent(onDismissRequest)
    }
}

@Composable
private fun DialogContent(onDismissRequest: () -> Unit) {
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
        modifier = Modifier
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
            Box(
                modifier = Modifier.size(150.dp).background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Watching...",
                    color = Color.White
                )
            }
        }
        AppButton(onClick = {
            onDismissRequest.invoke()
        }) {
            Text(
                text = "Cancel"
            )
        }
    }
}