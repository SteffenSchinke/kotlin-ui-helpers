package de.schinke.steffen.interfaces

import androidx.navigation.NamedNavArgument

interface AppRoute: AppRouteContent {

    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()
}
