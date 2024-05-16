package com.example.educationgallery.ui.photo_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ZoomableImage(imageUrl: String) {
    val painter = rememberImagePainter(data = imageUrl)
    val zoomState = if (painter.intrinsicSize != Size.Unspecified)
        rememberZoomState(contentSize = painter.intrinsicSize)
    else
        rememberZoomState()

    Image(
        painter = painter,
        contentDescription = "zoomable image",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .wrapContentSize()
            .zoomable(
                zoomState
            )

    )
}