package de.schinke.steffen.services

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import de.schinke.steffen.models.AppSnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppSnackbar {

    private var snackbarHostState: SnackbarHostState? = null

    private var _snackbarMessage = MutableStateFlow<AppSnackbarMessage?>(null)
    var snackbarMessage = _snackbarMessage.asStateFlow()

    fun setHost(hostState: SnackbarHostState) {
        snackbarHostState = hostState
    }

    suspend fun sendToSnackbar(message: AppSnackbarMessage) {

        _snackbarMessage.value = message

        snackbarHostState?.let { state ->
            val result = state.showSnackbar(
                message = message.message.toString(),
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
