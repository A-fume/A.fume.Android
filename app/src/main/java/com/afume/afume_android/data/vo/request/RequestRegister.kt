package com.afume.afume_android.data.vo.request

data class RequestRegister(
    val email : String,
    val nickname : String,
    val gender : String,
    val birth : Int,
    val password : String
)