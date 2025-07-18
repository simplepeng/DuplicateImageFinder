package simple.compose.digfinder.page

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.config.Router
import simple.compose.digfinder.page.main.MainAction
import simple.compose.digfinder.page.main.MainScreen
import simple.compose.digfinder.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Router.Main
        ) {
            composable<Router.Main> {
                MainScreen(onAction = { action ->
                    when (action) {
                        is MainAction.NavToFinder -> {
                            navController.navigate(Router.Finder(""))
                        }
                    }
                })
            }
            composable<Router.Finder> { backStackEntry ->
//                val projectMapper = backStackEntry.toRoute<ProjectMapper>()
//                FinderScreen(
//                    project = projectMapper.toProject(),
//                    onAction = { action ->
//                        when (action) {
//                            FinderAction.Back -> navController.navigateUp()
//                        }
//                    })
            }
        }
    }
}