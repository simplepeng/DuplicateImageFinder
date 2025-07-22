package simple.compose.digfinder.page

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.config.Router
import simple.compose.digfinder.page.finder.FinderAction
import simple.compose.digfinder.page.finder.FinderScreen
import simple.compose.digfinder.page.main.MainAction
import simple.compose.digfinder.page.main.MainScreen
import simple.compose.digfinder.theme.AppTheme

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = Router.Main
        ) {
            composable<Router.Main> {
                MainScreen(onAction = { action ->
                    when (action) {
                        is MainAction.NavToFinder -> {
                            navController.navigate(Router.Finder(action.project.id))
                        }
                    }
                })
            }
            composable<Router.Finder> { backStackEntry ->
                val projectId = backStackEntry.toRoute<Router.Finder>().id
                FinderScreen(
                    projectId = projectId,
                    onAction = { action ->
                        when (action) {
                            FinderAction.Back -> navController.navigateUp()
                        }
                    })
            }
        }
    }
}