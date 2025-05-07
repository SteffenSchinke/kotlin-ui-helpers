package de.schinke.steffen.interfaces

import androidx.compose.ui.graphics.vector.ImageVector

interface AppTabRoute: AppRoute {

    val title: String
    val icon: ImageVector
}