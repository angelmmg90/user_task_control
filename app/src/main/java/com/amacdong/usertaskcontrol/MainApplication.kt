package com.amacdong.usertaskcontrol

import android.app.Application
import com.amacdong.usertaskcontrol.data.database.UserTaskControlDatabase
import com.amacdong.usertaskcontrol.di.initDI

class MainApplication : Application() {

    companion object {
        lateinit var database: UserTaskControlDatabase
    }

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

}