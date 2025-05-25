package de.schinke.steffen.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.schinke.steffen.enums.SnackbarMode
import de.schinke.steffen.models.AppSnackbarMessage

@Composable
fun CustomSnackbar(

    modifier: Modifier = Modifier,
    snackbarMessage: AppSnackbarMessage,
    snackbarHostState: SnackbarHostState
) {

    Surface(

        shape = RoundedCornerShape(8.dp),
        tonalElevation = 6.dp,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .padding(8.dp)
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(8.dp))
            .zIndex(1f)
    ) {

        Column(

            modifier = Modifier.padding(16.dp)
        ) {

            if  (snackbarMessage.actionOnNewLine) {

                CustomSnackbarMessages(snackbarMessage)
                CustomSnackbarButtons(snackbarMessage, snackbarHostState)
            } else {

                Box(

                    modifier = modifier.fillMaxWidth()
                ) {

                    CustomSnackbarMessages(snackbarMessage)
                    CustomSnackbarButtons(snackbarMessage, snackbarHostState)
                }
            }
        }
    }
}


@Composable
private fun CustomSnackbarMessages(snackbarMessage: AppSnackbarMessage) {

    Column(
        modifier = Modifier.padding(start = 12.dp, top = 12.dp)
    ) {

        Row {

            Icon(
                painterResource(snackbarMessage.mode.iconId),
                "Icon",
                modifier = Modifier.padding(end = 16.dp),
                tint = if (snackbarMessage.mode == SnackbarMode.ERROR)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )

            Text(
                text = snackbarMessage.mode.title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(bottom = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (snackbarMessage.mode == SnackbarMode.ERROR)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = snackbarMessage.message,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        snackbarMessage.messageCode?.let {
            Text(
                text = it,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun CustomSnackbarButtons(

    snackbarMessage : AppSnackbarMessage,
    snackbarHostState: SnackbarHostState
) {

    Row(

        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        snackbarMessage.actionLabel?.let { title ->
            snackbarMessage.onAction?.let { onAction ->

                TextButton(onClick = {
                    onAction.invoke()
                    snackbarHostState.currentSnackbarData?.dismiss()
                }) {
                    Text(title)
                }
            }
        }

        if (snackbarMessage.withDismissAction) {

            Log.d("STS::CostumSnackbar", "set dismiss button start ...")

            IconButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                Icon(Icons.Default.Close, null, tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
