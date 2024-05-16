package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educationgallery.R
import com.example.educationgallery.ui.navigation.photo_screen_navigation.PhotoRouteName
import com.example.educationgallery.ui.photo_screen.components.SubjectFolderItem
import com.example.educationgallery.viewmodels.MainActivityViewModel
import com.example.educationgallery.viewmodels.PhotoViewModel

@Composable
fun SubjectListScreen(navController: NavController, viewModel: PhotoViewModel) {
    val subjects = viewModel.subjects.collectAsState()
    if (subjects.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.t),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                androidx.compose.material3.Text(
                    text = "Здесь пусто", color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp)) {
        items(subjects.value) {
            SubjectFolderItem(it){
                navController.navigate("${PhotoRouteName.LESSON_LIST_SCREEN.value}/${it.id}")
            }
        }
    }
}