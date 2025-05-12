package de.schinke.steffen.base_classs

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AppBaseViewModel<S>(initialState: S): ViewModel() {

    protected val viewModelName: String
        get() = this::class.simpleName ?: "UnknownViewModel"

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun setState(newState: (S) -> S) {

        _state.value = newState(_state.value)

        Log.d("STS::$viewModelName", "setState(${_state.value})")
    }
}