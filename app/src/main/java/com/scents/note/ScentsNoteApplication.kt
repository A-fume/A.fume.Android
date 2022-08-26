package com.scents.note

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.scents.note.data.local.preference.SharedPreferencesManager

class ScentsNoteApplication : Application() {

    companion object {
        lateinit var prefManager : SharedPreferencesManager
        private var instance: ScentsNoteApplication? = null
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