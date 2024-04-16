package com.example.educationgallery.ui.components.button_navigation

import com.example.educationgallery.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object Screen1: BottomItem("Расписание", R.drawable.baseline_access_time_24, "screen_1")
    object Screen2: BottomItem("Фотографии", R.drawable.baseline_camera_alt_24, "screen_2")
    object Screen3: BottomItem("Настройки", R.drawable.icon_settings, "screen_3")
}