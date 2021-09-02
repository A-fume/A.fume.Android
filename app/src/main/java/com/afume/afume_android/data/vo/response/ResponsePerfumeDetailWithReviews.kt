package com.afume.afume_android.data.vo.response

data class ResponsePerfumeDetailWithReviews(
    val message: String,
    val data: List<PerfumeDetailWithReviews>
)

data class PerfumeDetailWithReviews(
    val score: Float,
    val longevity: String,
    val sillage: String,
    val seasonal: List<String>,
    val gender: String,
    var access: Boolean,
    val content: String,
    val reviewIdx: Int,
    var likeCount: Int,
    val userGender: Int,
    val age: String,
    val nickname: String,
    val createTime: String
)