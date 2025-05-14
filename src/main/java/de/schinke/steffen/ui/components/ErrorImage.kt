package de.schinke.steffen.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.schinke.steffen.ui.R

@Composable
fun ErrorImage() {

    Icon(
        painter = painterResource(R.drawable.ic_warning),
        contentDescription = "Error",
        modifier = Modifier.size(60.dp),
        tint = MaterialTheme.colorScheme.tertiary.copy(0.1f)
    )
}