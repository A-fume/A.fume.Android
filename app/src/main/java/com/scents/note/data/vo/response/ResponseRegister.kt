package com.scents.note.data.vo.response

data class ResponseRegister(
    val userIdx : Int,
    val token : String,
    val refreshToken : String
)