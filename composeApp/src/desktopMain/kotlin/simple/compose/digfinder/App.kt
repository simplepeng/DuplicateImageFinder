package simple.compose.digfinder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.finder.FinderScreen
import simple.compose.digfinder.main.MainScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
//        MainScreen()
        FinderScreen()
    }
}