package com.example.educationgallery.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educationgallery.R
import com.example.educationgallery.model.App
import com.example.educationgallery.model.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
}