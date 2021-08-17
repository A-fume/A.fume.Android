package com.afume.afume_android

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.afume.afume_android.data.local.preference.SharedPreferencesManager

class AfumeApplication : Application() {

    companion object {
        lateinit var prefManager : SharedPreferencesManager
        private var instance: AfumeApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = SharedPreferencesManager(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}