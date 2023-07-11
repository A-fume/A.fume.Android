package com.scentsnote.android.utils.extension

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics

fun FirebaseAnalytics.setClickEvent(buttonName: String){
    firebaseAnalytics.logEvent("click_event") {
        param("button_name", buttonName)
    }
}

fun FirebaseAnalytics.setPageViewEvent(screenName: String, className: String){
    firebaseAnalytics.logEvent("page_view"){
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, className)
    }
}