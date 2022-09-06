package com.scentsnote.android.data.vo.response

data class ResponseRecommendPerfumeList(
    val count : Int,
    val rows : MutableList<RecommendPerfumeItem>
)

data class RecommendPerfumeItem(
    val perfumeIdx : Int,
    val name : String,
    val imageUrl : String,
    val brandName : String,
    var isLiked : Boolean,
    val keywordList : List<String>?
)
