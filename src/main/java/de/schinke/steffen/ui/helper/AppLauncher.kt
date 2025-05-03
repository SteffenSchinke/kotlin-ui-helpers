package de.schinke.steffen.ui.helper

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.time.Duration

@Composable
fun AppLauncher(

    duration: Duration,
    launchContent: @Composable () -> Unit,
    appContent: @Composable () -> Unit
) {

    var launched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        delay(duration)
        launched = true
    }

    if (launched) {

        appContent()
    } else {

        Box(

            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            launchContent()
        }
    }
}