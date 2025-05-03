package de.schinke.steffen.ui.helper

import androidx.navigation.NamedNavArgument

interface AppRoute {

    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()
}
