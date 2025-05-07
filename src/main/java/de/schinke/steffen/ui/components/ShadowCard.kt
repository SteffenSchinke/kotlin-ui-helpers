package de.schinke.steffen.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.schinke.steffen.enums.ShadowPosition

@Composable
fun ShadowCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
    shadowPositions: Set<ShadowPosition> = setOf(ShadowPosition.ALL),
    shadowColor: Color = Color.Black.copy(0.95f),
    backgroundColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    shape: Shape = RoundedCornerShape(12.dp),
    content: @Composable () -> Unit
) {

    Box(

        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor,
                clip = false
            )
    ) {

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (shadowPositions.contains(ShadowPosition.TOP) ||
                    shadowPositions.contains(ShadowPosition.ALL)) elevation else 0.dp,
                    bottom = if (shadowPositions.contains(ShadowPosition.BOTTOM) ||
                        shadowPositions.contains(ShadowPosition.ALL)) elevation else 0.dp,
                    start =  if (shadowPositions.contains(ShadowPosition.LEFT) ||
                        shadowPositions.contains(ShadowPosition.ALL)) elevation else 0.dp,
                    end = if (shadowPositions.contains(ShadowPosition.RIGHT) ||
                        shadowPositions.contains(ShadowPosition.ALL)) elevation else 0.dp
                ),
            shape = shape,
            border = BorderStroke(0.5.dp, borderColor),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {

            content()
        }
    }
}