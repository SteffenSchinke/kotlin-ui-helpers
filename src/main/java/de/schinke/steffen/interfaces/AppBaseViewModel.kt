package de.schinke.steffen.interfaces

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.schinke.steffen.models.AppSnackbarMessage
import de.schinke.steffen.services.AppSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AppBaseViewModel<S>(initialState: S): ViewModel() {

    protected var errorCode: String = ""
    protected var errorMessage: String = ""

    protected val nameViewModel: String
        get() = this::class.simpleName ?: "UnknownViewModel"

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun setState(newState: (S) -> S) {

        _state.value = newState(_state.value)

        Log.d(nameViewModel, "setState(${_state.value})")
    }

    protected fun sendSnackbarError(actionLabel: String? = null, actionAction: (() -> Unit)? = null) {

        viewModelScope.launch {

            AppSnackbar.send(
                AppSnackbarMessage(
                    message =
                        if (errorMessage.isEmpty()) {
                            "Leider ist ein Fehler aufgetreten!"
                        } else {
                            if (errorCode.isEmpty()) {
                                "Leider ist ein Fehler aufgetreten!\nBeschreibung: $errorMessage"
                            } else {
                                "Leider ist ein Fehler aufgetreten!\nBeschreibung: $errorMessage\nCode: $errorCode"
                            }
                        },
                    actionOnNewLine = true,
                    actionLabel = actionLabel,
                    onAction = actionAction,
                    duration = SnackbarDuration.Indefinite,
                    isErrorMessage = true
                )
            )
        }

        errorMessage = ""
        errorCode = ""

        Log.d(nameViewModel, "sendSnackbarError(message: ${errorMessage}, code: ${errorCode})")
    }

    protected fun sendSnackbarInfo(message: String) {

        viewModelScope.launch {

            AppSnackbar.send(
                AppSnackbarMessage(
                    message = message,
                    actionOnNewLine = true,
                    duration = SnackbarDuration.Short,
                    isErrorMessage = false
                )
            )
        }

        Log.d(nameViewModel, "sendSnackbarInfo($message)")
    }
}