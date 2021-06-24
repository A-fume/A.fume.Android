package com.afume.afume_android.data.vo.response

class ResponseHomePerfumeList (
    val count : Int,
    val rows : MutableList<HomePerfumeItem>
)

data class HomePerfumeItem(
    val perfumeIdx : Int,
    val name : String,
    val imageUrl : String,
    val brandName : String,
    var isLiked : Boolean
)