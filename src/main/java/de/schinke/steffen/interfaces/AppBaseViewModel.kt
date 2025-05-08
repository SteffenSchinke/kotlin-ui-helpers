package de.schinke.steffen.interfaces

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.schinke.steffen.models.AppSnackbarMessage
import de.schinke.steffen.services.AppSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AppBaseViewModel<S>(initialState: S): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _errorCode = MutableStateFlow("")
    val errorCode = _errorCode.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    protected fun setErrorCode(newCode: String) {

        _errorCode.value = newCode
    }

    protected fun setErrorMessage(newMessage: String) {

        _errorMessage.value = newMessage
    }

    protected fun setState(newState: (S) -> S) {

        _state.value = newState(_state.value)
    }

    protected fun sendSnackbarError(actionLabel: String? = null, actionAction: (() -> Unit)? = null) {

        viewModelScope.launch {

            AppSnackbar.send(
                AppSnackbarMessage(
                    message = "Leider ist ein Fehler aufgetreten!\nBeschreibung: ${_errorMessage.value}\nCode: ${_errorCode.value}",
                    actionOnNewLine = true,
                    actionLabel = actionLabel,
                    onAction = actionAction,
                    duration = SnackbarDuration.Indefinite,
                    isErrorMessage = true
                )
            )
        }
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
    }
}