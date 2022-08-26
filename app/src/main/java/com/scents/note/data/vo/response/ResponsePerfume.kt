package com.scents.note.data.vo.response

data class ResponsePerfume(
    val count: Int,
    val rows: MutableList<PerfumeInfo>
)
data class PerfumeInfo(
    val name: String,
    val englishName: String?=null,
    val mainSeriesIdx: Int?=null,
    val brandIdx: Int?=null,
    val imageUrl: String,
    val releaseDate: String?=null,
    val perfumeIdx: Int,
    val likeCnt: Int?=null,
    var isLiked: Boolean,
    val brandName: String,
    val mainSeriesName: String?=null
)
