package com.example.educationgallery.ui.navigation.button_navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.educationgallery.ui.navigation.photo_screen_navigation.PhotoNavGraph
import com.example.educationgallery.ui.schedule_screen.ScheduleFragmentScreen
import com.example.educationgallery.viewmodels.ScheduleViewModel

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = RouteName.SCHEDULE_SCREEN.value){
        composable(RouteName.SCHEDULE_SCREEN.value){
            val viewModel: ScheduleViewModel = viewModel()
            ScheduleFragmentScreen(viewModel = viewModel)
        }

        composable(RouteName.PHOTO_SCREEN.value){
            val photoNavHostController = rememberNavController()
            PhotoNavGraph(navHostController = photoNavHostController)
        }
    }
}