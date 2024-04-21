package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val tag = "MainActivityVM"

    fun syncSchedule(group: String){
        val scheduleDB = App.dataBase
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleDB.clearAllTables()
                try {
                    val parser = Parser(group)
                    parser.fillDB(scheduleDB)
                }
                catch (e: Exception){
                    Log.d(tag, e.message.toString())
                }
            }
        }
        Log.d(tag, group)
}