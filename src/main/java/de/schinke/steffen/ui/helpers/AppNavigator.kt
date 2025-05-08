package de.schinke.steffen.ui.helpers


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.schinke.steffen.interfaces.AppRoute
import de.schinke.steffen.interfaces.AppScreenContent
import de.schinke.steffen.interfaces.AppTabRoute
import de.schinke.steffen.services.AppSnackbar


@Composable
fun AppNavigator(

    startScreen: AppScreenContent,
    allRoutes: List<AppRoute>,
    allTabRoutes: List<AppTabRoute>
) {

    val navController = rememberNavController()
    val navCurrentBackStack by navController.currentBackStackEntryAsState()
    val navCurrentRoute = navCurrentBackStack?.destination?.route ?: "No Route"
    val navActiveScreen: AppScreenContent = allRoutes.find {
        it.route == navCurrentRoute && it is AppScreenContent
    } as? AppScreenContent ?: startScreen
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {

        AppSnackbar.setHost(snackbarHostState)
    }


    Scaffold(

        modifier = Modifier.fillMaxSize(),
        topBar = {

            navActiveScreen.TopBar(navController)
        },
        snackbarHost = {

            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {

            navActiveScreen.Fab(navController)
        },
        bottomBar = {

            if( navActiveScreen is AppTabRoute)  {

                    NavigationBar {

                        allTabRoutes.forEach { screen ->

                            NavigationBarItem(
                                icon = { Icon(screen.icon, null) },
                                label = { Text(screen.title) },
                                selected = navCurrentRoute == screen.route,
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