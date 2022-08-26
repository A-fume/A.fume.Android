package com.scents.note.data.vo.response

data class ResponsePerfumeDetail(
    val message: String,
    val data: PerfumeDetail
)
data class PerfumeDetail(
    val perfumeIdx: Int,
    val name: String,
    val imageUrls: List<String>,
    val brandName: String,
    val story: String,
    val abundanceRate: String,
    val volumeAndPrice: List<String>,
    val isLiked: Boolean,
    val Keywords: List<String>,
    val noteType: Int,
    val ingredients:Ingredients,
    val score: Float,
    val seasonal: Season,
    val sillage: Sillage,
    val englishName: String,
    val longevity:Longevity,
    val gender: Gender,
    var reviewIdx: Int
)
data class Season(
    val spring: Int,
    val summer: Int,
    val fall: Int,
    val winter: Int,
    val undefined: Int
)
data class Sillage(
    val light: Int,
    val medium: Int,
    val heavy: Int,
    val undefined: Int
)
data class Longevity(
    val veryWeak: Int,
    val weak: Int,
    val medium: Int,
    val strong: Int,
    val veryStrong : Int,
    val undefined: Int
)
data class Ingredients(
    val top: String,
    val middle: String,
    val base: String,
    val single: String
)
data class Gender(
    val male: Int,
    val neutral: Int,
    val female: Int,
    val undefined: Int
)