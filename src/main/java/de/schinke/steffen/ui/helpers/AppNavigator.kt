package de.schinke.steffen.ui.helpers


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.schinke.steffen.ui.interfaces.AppRoute
import de.schinke.steffen.ui.interfaces.AppScreenContent
import de.schinke.steffen.ui.interfaces.AppTabRoute


@Composable
fun AppNavigator(

    startScreen: AppScreenContent,
    allRoutes: List<AppRoute>,
    allTabRoutes: List<AppTabRoute>
) {

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "No Route"
    val activeScreen: AppScreenContent = allRoutes.find {
        it.route == currentRoute && it is AppScreenContent
    } as? AppScreenContent ?: startScreen

    Scaffold(

        modifier = Modifier.fillMaxSize(),
        topBar = {

            activeScreen.TopBar(navController)
        },
        snackbarHost = {

            activeScreen.SnackBar()
        },
        floatingActionButton = {

            activeScreen.Fab(navController)
        },
        bottomBar = {

            if( activeScreen is AppTabRoute)  {

                    NavigationBar {

                        allTabRoutes.forEach { screen ->

                            NavigationBarItem(
                                icon = { Icon(screen.icon, null) },
                                label = { Text(screen.title) },
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            },
        content = {

            innerPadding: PaddingValues ->

                Surface(

                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                ) {

                    NavHost(

                        navController = navController,
                        startDestination = (startScreen as AppRoute).route
                    ) {

                        allRoutes.forEach { screen ->

                            composable(screen.route, arguments = screen.arguments) {

                                (screen as AppScreenContent).apply {

                                    Content(navController)
                                }
                            }
                        }
                    }
                }
            }
    )
}

