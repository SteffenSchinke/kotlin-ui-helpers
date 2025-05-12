package de.schinke.steffen.base_classs

import android.app.Application
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.schinke.steffen.enums.SnackbarMode
import de.schinke.steffen.models.AppSnackbarMessage
import de.schinke.steffen.services.AppSnackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext.get

abstract class AppBaseAndroidViewModel<S>(application: Application, initialState: S): AndroidViewModel(application) {

    protected val viewModelName: String
        get() = this::class.simpleName ?: "UnknownViewModel"

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun setState(newState: (S) -> S) {

        _state.value = newState(_state.value)

        Log.d("STS::$viewModelName", "setState(${_state.value})")
    }

    protected fun showMessageInSnackbar(
        message: String,
        mode: SnackbarMode = SnackbarMode.INFO,
        messageCode: String? = null,
        actionOnNewLine: Boolean = false,
        actionLabel: String? = null,
        actionAction: (() -> Unit)? = null,
        withDismissAction: Boolean = true,
        duration: SnackbarDuration = SnackbarDuration.Short) {

        viewModelScope.launch {

            AppSnackbar.sendToSnackbar(
                AppSnackbarMessage(
                    mode = mode,
                    message = message,
                    messageCode = messageCode,
                    actionOnNewLine = actionOnNewLine,
                    actionLabel = actionLabel,
                    onAction = actionAction,
                    withDismissAction = withDismissAction,
                    duration = duration
                )
            )
        }

        Log.d("STS::$viewModelName", "showMessageInSnackbar(message: ${message}, mode: ${mode}, messageCode: ${messageCode}, actionOnNewLine: ${actionOnNewLine}, actionLabel: ${actionLabel}, actionAction: ${mode.title})")
    }
}