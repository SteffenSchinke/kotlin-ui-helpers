package de.schinke.steffen.extensions

import androidx.compose.ui.graphics.Color

fun Long.toColor(): Color {

    val red = ((this shr 16) and 0xFF).toInt()
    val green = ((this shr 8) and 0xFF).toInt()
    val blue = (this and 0xFF).toInt()

    return Color(red, green, blue)
}
