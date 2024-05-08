package com.example.educationgallery.ui.photo_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ZoomableImage(imageUrl: String) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val imagePainter = rememberImagePainter(data = imageUrl)

    Image(
        painter = imagePainter,
        contentDescription = "zoomable image",
        modifier = Modifier
            .graphicsLayer(
                scaleX = maxOf(1f, minOf(5f, scale)),
                scaleY = maxOf(1f, minOf(5f, scale)),
                translationX = maxOf(minOf(offset.x, 0f), -maxTranslationX(imagePainter, scale)),
                translationY = maxOf(minOf(offset.y, 0f), -maxTranslationY(imagePainter, scale))
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offset += pan * scale
                }
            }
    )
}

@Composable
private fun maxTranslationX(painter: Painter, scale: Float): Float {
    if (scale <= 1f) return 0f
    val imageWidth = painter.intrinsicSize.width
    val screenWidth = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    return (imageWidth * scale - screenWidth) / 2f
}

@Composable
private fun maxTranslationY(painter: Painter, scale: Float): Float {
    if (scale <= 1f) return 0f
    val imageHeight = painter.intrinsicSize.height
    val screenHeight = LocalDensity.current.run { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    return (imageHeight * scale - screenHeight) / 2f
}