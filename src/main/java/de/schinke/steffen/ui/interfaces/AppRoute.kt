package de.schinke.steffen.ui.interfaces

import androidx.navigation.NamedNavArgument

interface AppRoute {

    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()
}
