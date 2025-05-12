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

abstract class AppBaseAndroidViewModel<S>(application: Application, initialState: S): AndroidViewModel(application) {

    protected val viewModelName: String
        get() = this::class.simpleName ?: "UnknownViewModel"

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun setState(newState: (S) -> S) {

        _state.value = newState(_state.value)

        Log.d("STS::$viewModelName", "setState(${_state.value})")
    }
}