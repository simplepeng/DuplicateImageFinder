package simple.compose.digfinder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.data.DuplicateFile
import simple.compose.digfinder.data.ResultWrapper
import simple.compose.digfinder.finder.FinderAction
import simple.compose.digfinder.finder.FinderScreen
import simple.compose.digfinder.main.MainAction
import simple.compose.digfinder.main.MainScreen
import simple.compose.digfinder.result.ResultScreen
import simple.compose.digfinder.result.ResultScreenContent

@Composable
@Preview
fun App() {
    MaterialTheme {
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
                FinderScreen(onAction = { action ->
                    when (action) {
                        FinderAction.Back -> navController.navigateUp()
                        is FinderAction.NavToResult -> navController.navigate(Router.Result(""))
                    }
                })
            }
            composable<Router.Result> {
//                val duplicateFiles = it.toRoute<List<DuplicateFile>>()
//                ResultScreen(duplicateFiles)
            }
        }
    }
}