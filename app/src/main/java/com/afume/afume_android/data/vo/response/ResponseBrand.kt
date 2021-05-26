package com.afume.afume_android.data.vo.response

data class ResponseBrand(
    val count: Int,
    val rows: MutableList<InitialBrand>
)

data class InitialBrand(
    val firstInitial: String,
    val brand: BrandInfo
)

data class BrandInfo(
    val brandIdx: Int,
    val name: String="",
    val englishName: String="",
    val firstInitial: String="",
    val imageUrl: String="",
    val description: String="",
    var check: Boolean=false
)