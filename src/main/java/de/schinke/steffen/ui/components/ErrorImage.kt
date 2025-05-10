package de.schinke.steffen.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorImage() {

    Icon(
        Icons.Rounded.Warning,
        contentDescription = "Error",
        modifier = Modifier.size(60.dp),
        tint = MaterialTheme.colorScheme.tertiary.copy(0.1f)
    )
}