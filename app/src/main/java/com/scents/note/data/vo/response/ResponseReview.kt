package com.scents.note.data.vo.response

import com.google.gson.annotations.SerializedName

data class ResponseReview(
    val score : Float,
    val longevity : Int,
    val sillage : Int,
    val seasonal : List<String>,
    val gender: Int,
    val access : Boolean,
    val content : String,
    val reviewIdx : Int,
    @SerializedName("Perfume")
    val perfume : PerfumeItem,
    @SerializedName("KeywordList")
    val keyword : MutableList<KeywordInfo>,
    @SerializedName("Brand")
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