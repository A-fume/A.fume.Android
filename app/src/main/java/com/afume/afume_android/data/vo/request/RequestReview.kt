package com.afume.afume_android.data.vo.request

data class RequestReview(
    val score : Float,
    val longevity : String?,
    val sillage : String?,
    val seasonal : List<String>?,
    val gender : String?,
    val access : Boolean,
    val content : String,
    val keywordList : List<Int>?
)