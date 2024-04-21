package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educationgallery.model.App
import com.example.educationgallery.model.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel: ViewModel() {
    private val tag = "MainActivityVM"
    // Функция, которая синхронизирует расписание согласно группе
    // TODO: Нужно добавить обновление экрана к Игорю
    fun syncSchedule(group: String){
        val scheduleDB = App.dataBase
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleDB.clearAllTables()
                try {
                    val parser = Parser("ИКБО-06-22")
                    parser.fillDB(scheduleDB)
                }
                catch (e: Exception){
                    Log.d(tag, e.message.toString())
                }
            }
        }
        Log.d(tag, group)
    }
}