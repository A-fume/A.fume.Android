package com.scents.note.data.vo.response

data class ResponseLogin(
    val userIdx : Int,
    val nickname : String,
    val gender : String,
    val birth : Int,
    val token : String,
    val refreshToken : String
)