package de.schinke.steffen.models

import androidx.compose.material3.SnackbarDuration

data class AppSnackbarMessage(

    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDuration = SnackbarDuration.Short

)