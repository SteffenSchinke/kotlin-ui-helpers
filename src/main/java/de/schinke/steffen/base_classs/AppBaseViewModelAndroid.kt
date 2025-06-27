package de.schinke.steffen.base_classs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import de.schinke.steffen.enums.ViewModelState
import de.schinke.steffen.interfaces.AppError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AppBaseViewModelAndroid<E : AppError>(

    application: Application
) : AndroidViewModel(application) {

    protected val viewModelName: String
        get() = this::class.simpleName ?: "UnknownViewModel"

    private val _state = MutableStateFlow(ViewModelState.READY)
    val state = _state.asStateFlow()

    private val _error = MutableStateFlow<E?>(null)
    val error: StateFlow<E?> = _error

    protected fun setState(newState: ViewModelState) {
        _state.value = newState
        Log.d("STS::$viewModelName", "setState(${_state.value})")
    }

    protected fun setError(newValue: E) {
        _error.value = newValue
        _state.value = ViewModelState.ERROR
    }

    fun resetError() {
        _error.value = null
        _state.value = ViewModelState.READY
    }
}