package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGridScreen(navController: NavController, subjectId: Int, lessonId: Int) {
    val test = listOf(
        "https://random.dog/5cd73551-c42b-4c0f-bd80-634998e9a128.jpg",
        "https://random.dog/24178-5036-5513.jpg",
        "https://random.dog/a87a8d31-48dd-45b4-baab-e115dfa70692.jpg",
        "https://random.dog/1ddb2caa-41f5-456f-a446-f5a5335b5811.jpg",
        "https://random.dog/57f9587d-d6b1-4c05-acff-030c6affac57.png"
    )
    var showDialog by remember { mutableStateOf(false) }
    var selectedPhotoUrl by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(test) { photoUrl ->
            Image(
                painter = rememberImagePainter(data = photoUrl),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clickable {
                        selectedPhotoUrl = photoUrl
                        showDialog = true
                    },
                contentScale = ContentScale.Crop
            )
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                ZoomableImage(imageUrl = selectedPhotoUrl)
            }

        }
    }
}