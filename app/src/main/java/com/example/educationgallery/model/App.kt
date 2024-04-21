package com.example.educationgallery.model

import android.app.Application
import androidx.room.Room

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
        val dataBase by lazy {
            Room.databaseBuilder(
                instance.applicationContext,
                AppDatabase::class.java,
                "schedule_database"
            ).build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}