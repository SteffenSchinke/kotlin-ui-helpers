package de.schinke.steffen.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import de.schinke.steffen.enums.ViewModelState

interface AppScreenContent {

    val viewModel: AppBaseViewModel<ViewModelState>

    @Composable
    fun Content(navController: NavHostController)

    @Composable
    fun TopBar(navController: NavHostController): Unit? = null

    @Composable
    fun Fab(navController: NavHostController): Unit? = null
}