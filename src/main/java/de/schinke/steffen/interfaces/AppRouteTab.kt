package de.schinke.steffen.interfaces

import androidx.compose.runtime.Composable

interface AppRouteTab: AppRoute {

    @get:Composable
    val tabTitle: String

    val tabIcon: @Composable () -> Unit
}