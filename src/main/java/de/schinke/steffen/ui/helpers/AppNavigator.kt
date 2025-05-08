package de.schinke.steffen.ui.helpers


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
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
    val snackbarHostState = remember { SnackbarHostState() }
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "No Route"
    val actionOnNewLine by AppSnackbar.actionOnNewLine.collectAsState()
    var snackbarHeight by remember { mutableStateOf(0.dp) }
    val snackbarVisible = snackbarHostState.currentSnackbarData != null
    val activeScreen: AppScreenContent = allRoutes.find {
        it.route == currentRoute && it is AppScreenContent
    } as? AppScreenContent ?: startScreen
//    val fabBottomOffset by animateDpAsState(
//        targetValue = if (snackbarVisible) 80.dp else 16.dp,
//        label = "FAB Offset Animation"
//    )
    val fabBottomOffset by animateDpAsState(
        targetValue = if (snackbarVisible) snackbarHeight  else 16.dp,
        label = "FAB Offset Animation"
    )
   val fabEnterAnimation = remember {
        fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
    }
    val fabExitAnimation = remember {
        fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
    }
    val density = LocalDensity.current


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

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = if (activeScreen.Fab(navController) != null)
                                        snackbarHeight + 72.dp /* fb height */
                                    else
                                        0.dp),
                    snackbar = { snackbarData ->

                        Snackbar(
                            modifier = Modifier
                                .onSizeChanged { size ->

                                    with(density) {
                                        snackbarHeight = size.height.toDp()
                                    }
                                },
                            snackbarData = snackbarData,
                            shape = RoundedCornerShape(8.dp),
                            actionOnNewLine = actionOnNewLine,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            actionColor = MaterialTheme.colorScheme.primary,
                            actionContentColor = MaterialTheme.colorScheme.onPrimary,
                            dismissActionContentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
            }
        },
        floatingActionButton = {

            if (activeScreen.Fab(navController) != null) {
                AnimatedVisibility(
                    visible = true,
                    enter = fabEnterAnimation,
                    exit = fabExitAnimation
                ) {
                    Box(
                        modifier = Modifier.padding(bottom = fabBottomOffset, end = 16.dp)
                    ) {
                        activeScreen.Fab(navController)
                    }
                }
            }
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