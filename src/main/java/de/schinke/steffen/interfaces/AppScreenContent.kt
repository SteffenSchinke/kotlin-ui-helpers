package de.schinke.steffen.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface AppScreenContent {

    val content: @Composable (navController: NavHostController) -> Unit

    val tabBar: (@Composable (navController: NavHostController) -> Unit)?

    val fab: (@Composable (navController: NavHostController) -> Unit)?
}