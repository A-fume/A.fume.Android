package com.afume.afume_android.data.vo.request

data class RequestRegister(
    val email : String,
    val nickname : String,
    val password : String,
    val gender : String,
    val birth : Int
)