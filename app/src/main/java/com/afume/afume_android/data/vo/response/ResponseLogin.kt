package com.afume.afume_android.data.vo.response

data class ResponseLogin(
    val userIdx : Int,
    val accessToken : String,
    val refreshToken : String
)