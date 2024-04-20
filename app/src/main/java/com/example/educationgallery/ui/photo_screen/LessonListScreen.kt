package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educationgallery.ui.navigation.photo_screen_navigation.PhotoRouteName
import com.example.educationgallery.ui.photo_screen.components.LessonFolderItem
import com.example.educationgallery.viewmodels.PhotoViewModel

@Composable
fun LessonListScreen(navController: NavController, viewModel: PhotoViewModel, subjectId: Int) {
    viewModel.getLessons(subjectId)
    val lessons = viewModel.lessons.collectAsState()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp)){
        items(lessons.value) {
            LessonFolderItem(it){
                navController.navigate("${PhotoRouteName.PHOTO_SCREEN.value}/${subjectId}/${it.id}")
            }
        }
    }
}