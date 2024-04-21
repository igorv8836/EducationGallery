package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    // Функция, которая синхронизирует расписание согласно группе
    fun syncSchedule(group: String){
        Log.d("MainActivityViewModel", group)
    }
}