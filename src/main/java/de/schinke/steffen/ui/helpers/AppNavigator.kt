package de.schinke.steffen.ui.helpers


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import de.schinke.steffen.services.AppSnackbar
import de.schinke.steffen.interfaces.AppTabRoute
import androidx.compose.material3.SnackbarResult


@Composable
fun AppNavigator(

    startScreen: AppScreenContent,
    allRoutes: List<AppRoute>,
    allTabRoutes: List<AppTabRoute>
) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "No Route"
    val activeScreen: AppScreenContent = allRoutes.find {
        it.route == currentRoute && it is AppScreenContent
    } as? AppScreenContent ?: startScreen


    LaunchedEffect(Unit) {

        AppSnackbar.events.collect { snackbarMessage ->

            val result = snackbarHostState.showSnackbar(
                message = snackbarMessage.message,
                actionLabel = snackbarMessage.actionLabel,
                withDismissAction = snackbarMessage.withDismissAction,
                duration = snackbarMessage.duration
            )

            if (result == SnackbarResult.ActionPerformed) {
                snackbarMessage.onAction?.invoke()
            }
        }
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),
        topBar = {

            activeScreen.TopBar(navController)
        },
        snackbarHost = {

            SnackbarHost(hostState = snackbarHostState)
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

