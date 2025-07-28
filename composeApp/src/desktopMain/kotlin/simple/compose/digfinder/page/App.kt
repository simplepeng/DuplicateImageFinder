package simple.compose.digfinder.page

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import simple.compose.digfinder.page.Router
import simple.compose.digfinder.page.finder.FinderNavigation
import simple.compose.digfinder.page.finder.FinderScreen
import simple.compose.digfinder.page.main.MainNavigation
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
            MainScreen(onNavigation = { navigation ->
               when (navigation) {
                  is MainNavigation.ToFinder -> {
                     navController.navigate(Router.Finder(navigation.project.id))
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
                     FinderNavigation.Back -> navController.navigateUp()
                  }
               })
         }
      }
   }
}