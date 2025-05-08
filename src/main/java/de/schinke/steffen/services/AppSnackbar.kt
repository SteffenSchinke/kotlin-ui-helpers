package de.schinke.steffen.services

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import de.schinke.steffen.models.AppSnackbarMessage

object AppSnackbar {

    private var snackbarHostState: SnackbarHostState? = null

    fun setHost(hostState: SnackbarHostState) {
        snackbarHostState = hostState
    }

    suspend fun sendToSnackbar(message: AppSnackbarMessage) {
        snackbarHostState?.let { state ->
            val result = state.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                withDismissAction = message.withDismissAction,
                duration = message.duration
            )
            if (result == SnackbarResult.ActionPerformed) {
                message.onAction?.invoke()
            }
        }
    }
}
