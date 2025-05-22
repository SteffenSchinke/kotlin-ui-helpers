package de.schinke.steffen.extensions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.schinke.steffen.enums.SnackbarDisplayTime
import de.schinke.steffen.enums.SnackbarMode
import de.schinke.steffen.models.AppSnackbarMessage
import de.schinke.steffen.services.AppSnackbar
import kotlinx.coroutines.launch

fun ViewModel.sendMessageOnSnackbar(

    message: String,
    mode: SnackbarMode = SnackbarMode.INFO,
    messageCode: String? = null,
    actionOnNewLine: Boolean = false,
    actionLabel: String? = null,
    actionAction: (() -> Unit)? = null,
    withDismissAction: Boolean = true,
    duration: SnackbarDisplayTime = SnackbarDisplayTime.SHORT
) {

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

    Log.d("STS::${this::class.simpleName ?: "UnknownViewModel"}",
        "showMessageInSnackbar(message: ${message}, mode: ${mode}, messageCode: ${messageCode}, actionOnNewLine: ${actionOnNewLine}, actionLabel: ${actionLabel}, actionAction: ${mode.title})")
}
