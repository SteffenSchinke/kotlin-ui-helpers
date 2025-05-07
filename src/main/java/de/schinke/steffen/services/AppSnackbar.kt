package de.schinke.steffen.services

import de.schinke.steffen.models.AppSnackbarMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

object AppSnackbar {

    private val _events = MutableSharedFlow<AppSnackbarMessage>()
    val events: SharedFlow<AppSnackbarMessage> = _events.asSharedFlow()

    private val _actionOnNewLine = MutableStateFlow<Boolean>(false)
    val actionOnNewLine = _actionOnNewLine.asStateFlow()


    suspend fun send(message: AppSnackbarMessage) {

        _actionOnNewLine.value = message.actionOnNewLine
        _events.emit(message)
    }
}