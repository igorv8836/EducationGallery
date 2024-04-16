package com.example.educationgallery.ui.button_navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.educationgallery.ui.components.PhotoFragmentScreen
import com.example.educationgallery.ui.schedule_screen.ScheduleFragmentScreen
import com.example.educationgallery.viewmodels.PhotoViewModel
import com.example.educationgallery.viewmodels.ScheduleViewModel

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "screen_1"){
        composable("screen_1"){
            val viewModel: ScheduleViewModel = viewModel()
            ScheduleFragmentScreen(viewModel = viewModel)
        }

        composable("screen_2"){
            val viewModel: PhotoViewModel = viewModel()
            PhotoFragmentScreen()
        }

//        composable("screen_3"){
//            val viewModel: Setting = viewModel()
//            Setting
//        }
    }
}