package de.schinke.steffen.interfaces

import androidx.compose.runtime.Composable

interface AppRouteTab: AppRoute {

    val tabTitle: String
    val tabIcon: @Composable () -> Unit
}