package de.schinke.steffen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import de.schinke.steffen.ui.R


@Composable
fun CostumAsyncImage(

    modifier: Modifier = Modifier,
    url: String,
    size: Dp = 80.dp,
    roundedCorners: Dp = 12.dp,
    bgColor: Color = Color.White,
    borderSize: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    defaultIconColor: Color = Color.Gray,
    defaultIconInt: Int = R.drawable.ic_broken_image
) {

    val imagePixelSize = with(LocalDensity.current) { size.roundToPx() }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .size(Size(imagePixelSize, imagePixelSize))
            .build()
    )
    val actualState by painter.state.collectAsState()

    Box(

        modifier = modifier
            .border(
                width = borderSize,
                color = borderColor,
                shape = RoundedCornerShape(roundedCorners + 4.dp)
            )
            .clip(RoundedCornerShape(roundedCorners))
            .background(bgColor)
            .size(size),
        contentAlignment = Alignment.Center
    ) {

        when (actualState) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = "Cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            is AsyncImagePainter.State.Error -> {
                Icon(painter = painterResource(R.drawable.ic_broken_image),
                     contentDescription = "Error",
                     tint = defaultIconColor
                )
            }
            else -> {
                Icon(painter = painterResource(defaultIconInt),
                    contentDescription = "Error",
                    tint = defaultIconColor
                )
            }
        }
    }
}
