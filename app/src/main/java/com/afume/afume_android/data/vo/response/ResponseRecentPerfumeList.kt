package com.afume.afume_android.data.vo.response

class ResponseRecentPerfumeList (
    val count : Int,
    val rows : MutableList<RecentPerfumeItem>
)

data class RecentPerfumeItem(
    val perfumeIdx : Int,
    val name : String,
    val imageUrl : String,
    val brandName : String,
    val isLiked : Boolean
)