package com.scentsnote.android.data.vo

data class NewPerfumeListData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: NewPerfume
)

data class NewPerfume(
    val name: String
)
