package com.scentsnote.android.data.vo.response

data class ResponseWishList(
    val count: Int,
    val rows: MutableList<WishPerfume>
)

data class WishPerfume(
    val perfumeIdx : Int,
    val name : String,
    val brandName : String,
    val imageUrl : String,
    val isLiked : Boolean,
    val reviewIdx : Int
)
