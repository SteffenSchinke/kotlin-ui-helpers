package de.schinke.steffen.interfaces

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface AppRouteContent: AppRoute {

    @OptIn(ExperimentalMaterial3Api::class)
    val content: (@Composable (
        navController: NavHostController,
        sheetState: SheetState,
        args: Bundle?,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
        onDismiss: () -> Unit) -> Unit)?



    val tabBar: (@Composable (
        navController: NavHostController,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit
    ) -> Unit)?

    val fab: (@Composable (
        navController: NavHostController,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit
    ) -> Unit)?
}