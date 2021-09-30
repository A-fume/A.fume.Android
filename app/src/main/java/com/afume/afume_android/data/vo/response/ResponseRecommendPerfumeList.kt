package com.afume.afume_android.data.vo.response

data class ResponseRecommendPerfumeList(
    val count : Int,
    val rows : MutableList<RecommendPerfumeItem>
)

data class RecommendPerfumeItem(
    val perfumeIdx : Int,
    val name : String,
    val imageUrl : String,
    val brandName : String,
    val isLiked : Boolean,
    val keywordList : List<String>?
)
