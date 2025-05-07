package de.schinke.steffen.services

import de.schinke.steffen.models.AppSnackbarMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppSnackbar {

    private val _events = MutableSharedFlow<AppSnackbarMessage>()
    val events: SharedFlow<AppSnackbarMessage> = _events.asSharedFlow()

    suspend fun send(message: AppSnackbarMessage) {
        _events.emit(message)
    }
}