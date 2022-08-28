package com.scentsnote.android.data.vo.response

data class ResponseNewPerfumeList(
    val count : Int,
    val rows : MutableList<NewPerfumeItem>
)

data class NewPerfumeItem(
    val perfumeIdx : Int,
    val name : String,
    val imageUrl : String,
    val brandName : String,
    val isLiked : Boolean
)
