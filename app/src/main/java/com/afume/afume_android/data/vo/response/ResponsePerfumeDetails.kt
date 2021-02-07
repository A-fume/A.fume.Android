package com.afume.afume_android.data.vo.response

data class ResponsePerfumeDetails(
    val name: String,
    val englishName: String,
    val mainSeriesIdx: Int,
    val brandIdx: Int,
    val imageUrl: String,
    val releaseDate: String,
    val story: String,
    val abundanceRate: String,
    val volumeAndPrice: List<VolumeAndPrice>,
    val perfumeIdx: Int,
    val score: Float,
    val seasonal: Season,
    val sillage: Sillage,
    val longevity:Longevity,
    val noteType: Int,
    val ingredients:Ingredients
)

data class VolumeAndPrice(
    val volume: Int,
    val price: Int
)
data class Season(
    val spring: Int,
    val summer: Int,
    val fall: Int,
    val winter: Int
)
data class Sillage(
    val light: Int,
    val medium: Int,
    val heavy: Int
)
data class Longevity(
    val veryWeak: Int,
    val weak: Int,
    val normal: Int,
    val strong: Int,
    val veryStrong : Int
)
data class Ingredients(
    val top: List<IngredientsInfo>,
    val middle: List<IngredientsInfo>,
    val base: List<IngredientsInfo>
)
data class IngredientsInfo(
    val ingredientIdx: Int,
    val name: String,
    val description: String
)