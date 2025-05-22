package de.schinke.steffen.enums

import androidx.compose.material3.SnackbarDuration

enum class SnackbarDisplayTime {

    SHORT,
    LONG,
    INDEFINITE;

    fun toSnackbarDuration(): SnackbarDuration {
        return when (this) {
            SHORT -> SnackbarDuration.Short
            LONG -> SnackbarDuration.Long
            INDEFINITE -> SnackbarDuration.Indefinite
        }
    }
}