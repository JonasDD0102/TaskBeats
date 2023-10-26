package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.Local.AppDataBase

class TaskBeatsApplication:Application(){

    lateinit var dataBase: AppDataBase
    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,"name"
        ).build()

    }

    fun getAppDataBase(): AppDataBase {
        return dataBase
    }
}

