package de.schinke.steffen.interfaces

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface AppRouteContent {

    val content: (@Composable (
        navController: NavHostController,
        onShowSheet: (AppRouteSheet) -> Unit
    ) -> Unit)?

    @OptIn(ExperimentalMaterial3Api::class)
    val contentSheet: (@Composable (
        navController: NavHostController,
        onShowSheet: SheetState, () -> Unit
    ) -> Unit)?

    val tabBar: (@Composable (
        navController: NavHostController,
        onShowSheet: (AppRouteSheet) -> Unit
    ) -> Unit)?

    val fab: (@Composable (
        navController: NavHostController,
        onShowSheet:(AppRouteSheet) -> Unit
    ) -> Unit)?
}