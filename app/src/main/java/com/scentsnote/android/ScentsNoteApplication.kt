package com.scentsnote.android

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.scentsnote.android.data.local.preference.SharedPreferencesManager
import com.scentsnote.android.utils.etc.Log

class ScentsNoteApplication : Application() {

    companion object {
        lateinit var prefManager : SharedPreferencesManager
        lateinit var firebaseAnalytics: FirebaseAnalytics

        private var instance: ScentsNoteApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = SharedPreferencesManager(applicationContext)
        firebaseAnalytics = Firebase.analytics
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Log.getInstance()
    }
}