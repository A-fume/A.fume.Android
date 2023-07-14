package com.scentsnote.android.utils.extension

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics

fun FirebaseAnalytics.setClickEvent(buttonName: String){
    firebaseAnalytics.logEvent("click_event") {
        param("button_name", buttonName)
    }
}

fun FirebaseAnalytics.setOneParamClickEvent(key1: String, value1: String){
    firebaseAnalytics.logEvent("click_event") {
        param(key1, value1)
    }
}

fun FirebaseAnalytics.setTwoParamClickEvent(key1: String, value1: String, key2: String, value2: String){
    firebaseAnalytics.logEvent("click_event") {
        param(key1, value1)
        param(key2,value2)
    }
}

fun FirebaseAnalytics.setHeartBtnClickEvent(buttonArea: String, like : Boolean){
    val buttonAction = if(like) "off" else "on"

    firebaseAnalytics.logEvent("click_event") {
        param("button_name", "HeartButton")
        param("heart_name", buttonArea)
        param("heart_ox", buttonAction)
    }
}

fun FirebaseAnalytics.setPageViewEvent(screenName: String, className: String){
    firebaseAnalytics.logEvent("page_view"){
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, className)
    }
}