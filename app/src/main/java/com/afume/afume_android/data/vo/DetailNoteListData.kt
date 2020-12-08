package com.afume.afume_android.data.vo

import android.provider.ContactsContract

data class DetailNoteListData(
    val age: String,
    val gender: String,
    val contents: String,
    val rate: Float,
    val favorite: Int,
    val like:Int,
    val nickname: String
)