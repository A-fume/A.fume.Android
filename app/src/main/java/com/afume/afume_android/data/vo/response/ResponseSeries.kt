package com.afume.afume_android.data.vo.response

data class ResponseSeries(
    val count:Int,
    val rows: MutableList<SeriesInfo>
)

data class SeriesInfo(
    val seriesIdx: Int=1,
    val name: String="시트러스",
    val englishName: String="",
    val imageUrl: String="",
    val description: String="",
    val ingredients: MutableList<SeriesIngredients>,
    var isLiked: Boolean=false
)

data class SeriesIngredients(
    val ingredientIdx:Int=2,
    val name: String="베르가못",
    val imageUrl: String=" ",
    val seriesIdx: Int=2,
    var checked: Boolean= false,
    var clickable: Boolean=true
)