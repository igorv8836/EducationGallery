package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import coil.compose.rememberImagePainter

@Composable
fun ZoomableImage(imageUrl: String) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Image(
        painter = rememberImagePainter(data = imageUrl),
        contentDescription = "zoomable image",
        modifier = Modifier
            .graphicsLayer(
                scaleX = maxOf(1f, minOf(5f, scale)),
                scaleY = maxOf(1f, minOf(5f, scale)),
                translationX = offset.x,
                translationY = offset.y
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offset = offset.plus(pan)
                }
            }
    )
}
