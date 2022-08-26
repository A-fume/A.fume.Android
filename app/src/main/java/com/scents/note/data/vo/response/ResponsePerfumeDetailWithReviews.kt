package com.scents.note.data.vo.response

data class ResponsePerfumeDetailWithReviews(
    val message: String,
    val data: List<PerfumeDetailWithReviews>
)

data class PerfumeDetailWithReviews(
    val reviewIdx: Int,
    val score: Float,
    var access: Boolean,
    val content: String,
    var likeCount: Int,
    var isLiked: Boolean,
    val userGender: Int,
    val age: String,
    val nickname: String,
    val createTime: String,
    val isReported: Boolean
)