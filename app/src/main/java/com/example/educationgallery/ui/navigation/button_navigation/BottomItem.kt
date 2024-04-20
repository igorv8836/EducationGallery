package com.example.educationgallery.ui.navigation.button_navigation

import com.example.educationgallery.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    data object Screen1 : BottomItem(
        "Расписание", R.drawable.baseline_access_time_24, RouteName.SCHEDULE_SCREEN.value
    )

    data object Screen2 :
        BottomItem("Фотографии", R.drawable.baseline_camera_alt_24, RouteName.PHOTO_SCREEN.value)
}