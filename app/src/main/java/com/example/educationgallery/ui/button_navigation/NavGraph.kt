package com.example.educationgallery.ui.button_navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.educationgallery.ui.photo_screen.PhotoFragmentScreen
import com.example.educationgallery.ui.schedule_screen.ScheduleFragmentScreen
import com.example.educationgallery.viewmodels.PhotoViewModel
import com.example.educationgallery.viewmodels.ScheduleViewModel

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = RouteName.SCHEDULE_SCREEN.value){
        composable(RouteName.SCHEDULE_SCREEN.value){
            val viewModel: ScheduleViewModel = viewModel()
            ScheduleFragmentScreen(viewModel = viewModel)
        }

        composable(RouteName.PHOTO_SCREEN.value){
            val viewModel: PhotoViewModel = viewModel()
            PhotoFragmentScreen(viewModel = viewModel)
        }

//        composable("screen_3"){
//            val viewModel: Setting = viewModel()
//            Setting
//        }
    }
}