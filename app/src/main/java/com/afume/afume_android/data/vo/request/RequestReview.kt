package com.afume.afume_android.data.vo.request

data class RequestReview(
    val score : Float,
    val longevity : Int?,
    val sillage : Int?,
    val seasonal : List<String>?,
    val gender : Int?,
    val access : Boolean,
    val content : String,
    val keywordList : List<Int>?
)