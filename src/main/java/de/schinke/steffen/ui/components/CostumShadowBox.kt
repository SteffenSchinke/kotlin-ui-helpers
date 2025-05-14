package de.schinke.steffen.ui.components


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.schinke.steffen.enums.ShadowPosition

@Composable
fun CostumShadowBox(
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    shadowPositions: Set<ShadowPosition> = setOf(ShadowPosition.RIGHT, ShadowPosition.TOP),
    shadowColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    borderSize: Dp = 1.dp,
    cornerRadius: Dp = 24.dp,
    content: @Composable () -> Unit
) {

    val shape: Shape = RoundedCornerShape(cornerRadius + cornerRadius / 3)

    Box(
        modifier = modifier
//            .fillMaxWidth()
            .shadowByPosition(
                color = shadowColor,
                alpha = 0.5f,
                blurRadius = elevation,
                cornerRadius = cornerRadius,
                shadowPositions = shadowPositions
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(borderSize, borderColor, shape)
    ) {
        content()
    }

    Spacer(modifier = Modifier.padding(top = elevation))
}

// ChatGPT
@SuppressLint("SuspiciousModifierThen")
fun Modifier.shadowByPosition(
    color: Color,
    alpha: Float = 0.5f,
    blurRadius: Dp = 0.dp,
    cornerRadius: Dp = 0.dp,
    shadowPositions: Set<ShadowPosition>
): Modifier {
    var result = this

    if (shadowPositions.contains(ShadowPosition.LEFT)) {
        result = result.then(
            Modifier.drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        this.color = color.copy(alpha = 0.01f).toArgb()
                        setShadowLayer(
                            blurRadius.toPx(),
                            -blurRadius.toPx(),
                            0f,
                            color.copy(alpha = alpha).toArgb()
                        )
                    }

                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        cornerRadius.toPx(),
                        cornerRadius.toPx(),
                        paint
                    )
                }
            }
        )
    }

    if (shadowPositions.contains(ShadowPosition.RIGHT)) {
        result = result.then(
            Modifier.drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        this.color = color.copy(alpha = 0.01f).toArgb()
                        setShadowLayer(
                            blurRadius.toPx(),
                            blurRadius.toPx(),
                            0f,
                            color.copy(alpha = alpha).toArgb()
                        )
                    }

                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        cornerRadius.toPx(),
                        cornerRadius.toPx(),
                        paint
                    )
                }
            }
        )
    }

    if (shadowPositions.contains(ShadowPosition.TOP)) {
        result = result.then(
            Modifier.drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        this.color = color.copy(alpha = 0.01f).toArgb()
                        setShadowLayer(
                            blurRadius.toPx(),
                            0f,
                            -blurRadius.toPx(),
                            color.copy(alpha = alpha).toArgb()
                        )
                    }

                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        cornerRadius.toPx(),
                        cornerRadius.toPx(),
                        paint
                    )
                }
            }
        )
    }

    if (shadowPositions.contains(ShadowPosition.BOTTOM)) {
        result = result.then(
            Modifier.drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        this.color = color.copy(alpha = 0.01f).toArgb()
                        setShadowLayer(
                            blurRadius.toPx(),
                            0f,
                            blurRadius.toPx(),
                            color.copy(alpha = alpha).toArgb()
                        )
                    }

                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        cornerRadius.toPx(),
                        cornerRadius.toPx(),
                        paint
                    )
                }
            }
        )
    }

    return result
}
