package de.schinke.steffen.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import de.schinke.steffen.ui.R

@Composable
fun CloseButton(

    onDismiss: () -> Unit
) {

    IconButton(onClick = { onDismiss() }) {
        Icon(painterResource(R.drawable.ic_close), "Close",
        tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}