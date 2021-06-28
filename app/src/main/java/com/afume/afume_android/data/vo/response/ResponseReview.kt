package com.afume.afume_android.data.vo.response

data class ResponseReview(
    val score : Float,
    val longevity : Int,
    val sillage : Int,
    val seasonal : List<String>,
    val gender: Int,
    val access : Boolean,
    val content : String,
    val reviewIdx : Int,
    val perfume : PerfumeItem,
    val keyword : MutableList<KeywordInfo>,
    val brand : BrandItem
    )

data class PerfumeItem(
    val perfumeIdx : Int,
    val perfumeName : String,
    val imageUrl : String
)

data class BrandItem(
    val brandIdx : Int,
    val brandName : String
)