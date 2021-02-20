package com.afume.afume_android.data.vo.response

data class ResponseSeries(
    val count:Int,
    val rows: List<SeriesInfo>
)

data class SeriesInfo(
    val name: String,
    val englishName: String,
    val description: String="",
    val seriesIdx: Int,
    val imageUrl: Int,
    var isLiked: Boolean=false
)