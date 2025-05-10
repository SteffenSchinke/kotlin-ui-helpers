package de.schinke.steffen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun AsyncImage(

    modifier: Modifier = Modifier,
    url: String,
    size: Dp = 80.dp,
    roundedCorners: Dp = 12.dp,
    bgColor: Color = Color.White,
    borderColor: Color = Color.Unspecified
) {

    val imageSize = (size.value * LocalDensity.current.density).toInt()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .size(imageSize)
            .build()
    )
    val state = painter.state

    Box(

        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(roundedCorners + 4.dp)
            )
            .padding(10.dp)
            .clip(RoundedCornerShape(roundedCorners))
            .background(bgColor)
            .size(size),
        contentAlignment = Alignment.Center
    ) {

        when (state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = "Cover",
                    modifier = Modifier.fillMaxSize()
                )
            }
            is AsyncImagePainter.State.Error -> {
                Icon(Icons.Default.BrokenImage, contentDescription = "Error")
            }
            else -> {
                Icon(Icons.Default.BrokenImage, contentDescription = "Error")
            }
        }
    }
}
