package com.afume.afume_android.data.vo.response

data class ResponseRegister(
    val userIdx : Int,
    val token : String,
    val refreshToken : String
)