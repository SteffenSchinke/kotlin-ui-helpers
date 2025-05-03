package de.schinke.steffen.ui.interfaces

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface AppScreenContent {

    @Composable
    fun Content(innerPadding: PaddingValues,
                navController: NavHostController)

    @Composable
    fun TopBar(navController: NavHostController): (@Composable () -> Unit)? = null

    @Composable
    fun SnackBar(): (@Composable () -> Unit)? = null

    @Composable
    fun Fab(navController: NavHostController): (@Composable () -> Unit)? = null
}