package simple.compose.digfinder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.finder.FinderScreen
import simple.compose.digfinder.main.MainAction
import simple.compose.digfinder.main.MainScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
//        MainScreen()
        FinderScreen()
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Router.Main
        ) {
            composable<Router.Main> {
                MainScreen(onAction = { action ->
                    when (action) {
                        MainAction.NavToFinder -> navController.navigate(Router.Finder)
                    }
                })
            }
            composable<Router.Finder> {
                FinderScreen()
            }
            composable<Router.Result> {

            }
        }
    }
}