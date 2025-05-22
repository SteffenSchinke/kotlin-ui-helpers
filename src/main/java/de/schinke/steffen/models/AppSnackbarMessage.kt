package de.schinke.steffen.models

import de.schinke.steffen.enums.SnackbarDisplayTime
import de.schinke.steffen.enums.SnackbarMode

data class AppSnackbarMessage(

    val mode: SnackbarMode,
    val message: String,
    val messageCode: String? = null,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val actionOnNewLine: Boolean = false,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDisplayTime = SnackbarDisplayTime.SHORT
)