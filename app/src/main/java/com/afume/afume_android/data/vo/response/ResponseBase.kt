package com.afume.afume_android.data.vo.response

data class ResponseBase<T>(
    val message: String,
    val data: T
)

data class ResponseMessage(
    val message: String
)