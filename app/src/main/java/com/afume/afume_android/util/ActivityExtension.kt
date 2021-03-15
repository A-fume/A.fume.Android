package com.afume.afume_android.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startActivity(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun AppCompatActivity.startActivityWithFinish(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}