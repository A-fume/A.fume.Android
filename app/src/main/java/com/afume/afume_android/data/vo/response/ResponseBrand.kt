package com.afume.afume_android.data.vo.response

data class ResponseBrand(
    val count: Int,
    val rows: MutableList<InitialBrand>
)

data class InitialBrand(
    val firstInitial: String,
    val brands: MutableList<BrandInfo>
)

data class BrandInfo(
    val brandIdx: Int,
    val name: String="",
    var check: Boolean=false,
    var clickable: Boolean=true
)