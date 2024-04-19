package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.educationgallery.viewmodels.PhotoViewModel

@Composable
fun PhotoFragmentScreen(viewModel: PhotoViewModel) {
    MaterialTheme {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(100) {
                FolderItem()
            }
        }
    }
}