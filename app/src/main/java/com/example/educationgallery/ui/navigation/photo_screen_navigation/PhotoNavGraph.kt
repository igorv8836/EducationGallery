package com.example.educationgallery.ui.navigation.photo_screen_navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.educationgallery.ui.photo_screen.LessonListScreen
import com.example.educationgallery.ui.photo_screen.PhotoGridScreen
import com.example.educationgallery.ui.photo_screen.SubjectListScreen
import com.example.educationgallery.viewmodels.PhotoViewModel

@Composable
fun PhotoNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = PhotoRouteName.SUBJECT_LIST_SCREEN.value
    ) {
        composable(PhotoRouteName.SUBJECT_LIST_SCREEN.value) {
            val viewModel: PhotoViewModel = viewModel()
            SubjectListScreen(navHostController)
        }
        composable(
            "${PhotoRouteName.LESSON_LIST_SCREEN.value}/{subjectId}",
            arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
        ) {
            LessonListScreen(
                navHostController,
                it.arguments?.getInt("subjectId") ?: -1
            )
        }
        composable("${PhotoRouteName.PHOTO_SCREEN.value}/{subjectId}/{lessonId}",
            arguments = listOf(
                navArgument("subjectId") { type = NavType.IntType },
                navArgument("lessonId") { type = NavType.IntType }
            )
        ) {
            PhotoGridScreen(
                navHostController,
                it.arguments?.getInt("subjectId") ?: -1,
                it.arguments?.getInt("lessonId") ?: -1
            )
        }
    }
}