package com.example.educationgallery.ui.photo_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educationgallery.ui.navigation.photo_screen_navigation.PhotoRouteName

@Composable
fun SubjectListScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
        items(20) {
            SubjectFolderItem(){
                navController.navigate("${PhotoRouteName.LESSON_LIST_SCREEN.value}/1")
            }
        }
    }
}