package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.educationgallery.R
import com.example.educationgallery.ui.photo_screen.components.ZoomableImage
import com.example.educationgallery.viewmodels.PhotoViewModel


@Composable
fun PhotoGridScreen(
    navController: NavController,
    viewModel: PhotoViewModel,
    subjectId: Int,
    lessonId: Int
) {
    viewModel.getPhotos(subjectId, lessonId)
    var showDialog by remember { mutableStateOf(false) }
    var selectedPhotoUrl by remember { mutableStateOf("") }

    val photos = viewModel.photos.collectAsState()

    if (photos.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.t),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = "Здесь пусто", color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(photos.value) { photoUrl ->
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
            Box(contentAlignment = Alignment.Center, modifier = Modifier.wrapContentSize()){
                ZoomableImage(imageUrl = selectedPhotoUrl)
            }

        }
    }
}