package de.schinke.steffen.extensions

import androidx.compose.ui.graphics.Color

fun Color.toLong(): Long {

    val alpha = (alpha * 255).toInt() and 0xFF
    val red = (red * 255).toInt() and 0xFF
    val green = (green * 255).toInt() and 0xFF
    val blue = (blue * 255).toInt() and 0xFF

    return ((alpha.toLong() shl 24) or
            (red.toLong() shl 16) or
            (green.toLong() shl 8) or
            blue.toLong())
}

fun Color.invert(): Color {

    val red = (255 - (red * 255)).toInt()
    val green = (255 - (green * 255)).toInt()
    val blue = (255 - (blue * 255)).toInt()

    return Color(red, green, blue)
}
