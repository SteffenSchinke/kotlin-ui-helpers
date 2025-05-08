package de.schinke.steffen.models

import androidx.compose.material3.SnackbarDuration
import de.schinke.steffen.enums.SnackbarMode

data class AppSnackbarMessage(

    val mode: SnackbarMode,
    val message: String,
    val messageCode: String? = null,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val actionOnNewLine: Boolean = false,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDuration = SnackbarDuration.Short
)