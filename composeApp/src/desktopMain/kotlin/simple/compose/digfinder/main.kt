package simple.compose.digfinder

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import simple.compose.digfinder.page.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DuplicateImageFinder",
    ) {
        App()
    }
}