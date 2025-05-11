package de.schinke.steffen.services

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import de.schinke.steffen.enums.SnackbarMode
import de.schinke.steffen.models.AppSnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppSnackbar {

    private var snackbarHostState: SnackbarHostState? = null

    private val _snackbarNotificationMode =
        MutableStateFlow<MutableList<SnackbarMode>>(
            mutableListOf(
                SnackbarMode.ERROR,
                SnackbarMode.INFO
            )
        )

    private val _snackbarMessage = MutableStateFlow<AppSnackbarMessage?>(null)
    val snackbarMessage = _snackbarMessage.asStateFlow()

    fun setHost(hostState: SnackbarHostState) {
        snackbarHostState = hostState
    }

    fun addNotificationMode(mode: SnackbarMode) {

        if (!_snackbarNotificationMode.value.contains(mode)) {
            _snackbarNotificationMode.value.add(mode)
        }
    }

    fun removeNotificationMode(mode: SnackbarMode) {

        if (_snackbarNotificationMode.value.contains(mode)) {
            _snackbarNotificationMode.value.remove(mode)
        }
    }

    fun isNotificationModeEnabled(mode: SnackbarMode): Boolean {

        return _snackbarNotificationMode.value.contains(mode)
    }

    suspend fun sendToSnackbar(message: AppSnackbarMessage) {

        if (_snackbarNotificationMode.value.contains(message.mode)) {

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
}
