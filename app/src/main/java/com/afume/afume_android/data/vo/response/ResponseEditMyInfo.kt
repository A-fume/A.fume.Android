package com.afume.afume_android.data.vo.response

data class ResponseEditMyInfo(
    val userIdx : Int,
    val nickname : String,
    val gender : String,
    val birth : Int,
    val email : String
)