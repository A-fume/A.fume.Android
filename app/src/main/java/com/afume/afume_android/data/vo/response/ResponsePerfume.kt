package com.afume.afume_android.data.vo.response

data class ResponsePerfume(
    val count: Int,
    val rows: List<PerfumeInfo>
)
data class PerfumeInfo(
    val name: String,
    val englishName: String,
    val mainSeriesIdx: Int,
    val brandIdx: Int,
    val imageUrl: String,
    val releaseDate: String,
    val perfumeIdx: Int,
    val likeCnt: Int,
    val isLiked: Boolean,
    val brandName: String,
    val mainSeriesName: String
)
