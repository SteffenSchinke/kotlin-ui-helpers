package de.schinke.steffen.ui.helper

import androidx.compose.ui.graphics.vector.ImageVector

interface AppTabRoute: AppRoute {

    val title: String
    val icon: ImageVector
}