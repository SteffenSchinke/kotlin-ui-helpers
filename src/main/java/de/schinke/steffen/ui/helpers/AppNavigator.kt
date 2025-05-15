package de.schinke.steffen.ui.helpers


import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.schinke.steffen.interfaces.AppRoute
import de.schinke.steffen.interfaces.AppRouteContent
import de.schinke.steffen.interfaces.AppRouteSheet
import de.schinke.steffen.interfaces.AppRouteTab
import de.schinke.steffen.services.AppSnackbar
import de.schinke.steffen.ui.components.CustomSnackbar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator(

    startScreen: AppRouteContent,
    allRoutes: List<AppRoute>,
    allTabRoutes: List<AppRouteTab>
) {

    val navController = rememberNavController()
    val navCurrentBackStack by navController.currentBackStackEntryAsState()
    val navCurrentRoute = navCurrentBackStack?.destination?.route ?: "No Route Found"
    val navActiveScreen: AppRouteContent = allRoutes.find {
        it.route == navCurrentRoute
    } as? AppRouteContent ?: startScreen

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarHeight by remember { mutableStateOf(0.dp) }
    val isSnackbarVisible = snackbarHostState.currentSnackbarData != null

    var activeSheet by remember { mutableStateOf<AppRouteSheet?>(null) }
    var sheetArgs by remember { mutableStateOf<Bundle?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val density = LocalDensity.current
    var fabHeight by remember { mutableStateOf(0.dp) }
    val fabBottomOffset by animateDpAsState(
        targetValue = if (isSnackbarVisible) snackbarHeight else 0.dp,
        label = "FAB Offset Animation"
    )
    val fabEnterAnimation = remember {
        fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
    }
    val fabExitAnimation = remember {
        fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
    }
    val onDismiss: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            activeSheet = null
        }
    }
    val showSheet: (AppRouteSheet, Bundle?) -> Unit = { sheet, args ->
        coroutineScope.launch {
            sheetArgs = args
            activeSheet = sheet
            sheetState.show()
        }
    }

    Log.d("STS::AppNavigator", "AppNavigator start mit route(${navCurrentRoute}) ...")

    LaunchedEffect(Unit) {
        AppSnackbar.setHost(snackbarHostState)
    }

    activeSheet?.let {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState
        ) {

            Log.d("STS::AppNavigator", "${activeSheet!!::class.simpleName ?: "Unbekanter Sheet"} sheet start ...")
            it.contentSheet(navController, sheetState, sheetArgs, showSheet, onDismiss)
        }
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),

        snackbarHost = {

            AppSnackbar.snackbarMessage.collectAsState().value?.let { message ->

                Log.d("STS::AppNavigator::${navCurrentRoute}", "snackbarHost start ...")

                Box(modifier = Modifier.fillMaxSize()) {

                    SnackbarHost(

                        hostState = snackbarHostState,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(
                                y =
                                    if (navActiveScreen.fab != null)
                                        snackbarHeight + fabHeight
                                    else
                                        0.dp
                            ),
                        snackbar = {
                            CustomSnackbar(
                                modifier = Modifier
                                    .onSizeChanged { size ->
                                        snackbarHeight = with(density) { size.height.toDp() }
                                    },
                                snackbarMessage = message,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    )
                }
            }
        },

        topBar = {

            navActiveScreen.topBar?.let { topBar ->

                Log.d("STS::AppNavigator::${navCurrentRoute}", "topBar start ...")

                topBar(navController, showSheet)
            }
        },

        floatingActionButton = {

            navActiveScreen.fab?.let { fab ->

                Log.d("STS::AppNavigator::${navCurrentRoute}", "fab start ...")

                AnimatedVisibility(

                    visible = true,
                    enter = fabEnterAnimation,
                    exit = fabExitAnimation
                ) {

                    Box(
                        modifier = Modifier
                            .padding(bottom = fabBottomOffset)
                            .onPlaced { layout ->
                                fabHeight =
                                    with(density) { layout.size.height.toDp() + 12.dp /* padding fab */ }
                            }) {

                        fab.invoke(navController, showSheet)
                    }
                }
            }
        },

        bottomBar = {

            Log.d("STS::AppNavigator::${navCurrentRoute}", "bottom bar start ...")

            if (navActiveScreen is AppRouteTab) {

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

        content = { innerPadding: PaddingValues ->

            Log.d("STS::AppNavigator::${navCurrentRoute}", "content start ...")

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
                            (screen as AppRouteContent).content?.let { content ->

                                Log.d("STS::AppNavigator", "start content ...")

                                content(
                                    navController,
                                    sheetState,
                                    sheetArgs,
                                    showSheet,
                                    onDismiss
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}