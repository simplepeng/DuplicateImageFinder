package simple.compose.digfinder.ext

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import simple.compose.digfinder.config.SystemConfig

fun Modifier.debugBackground(): Modifier {
    return if (SystemConfig.isDebug) {
        Modifier.background(Color.Red)
    } else {
        this
    }
}