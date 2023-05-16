package com.scentsnote.android.data.vo.response

import androidx.recyclerview.widget.DiffUtil

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
){
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PerfumeDetailWithReviews>() {
            override fun areItemsTheSame(
                oldItem: PerfumeDetailWithReviews,
                newItem: PerfumeDetailWithReviews
            ): Boolean =
                oldItem.reviewIdx == newItem.reviewIdx

            override fun areContentsTheSame(
                oldItem: PerfumeDetailWithReviews,
                newItem: PerfumeDetailWithReviews
            ): Boolean =
                oldItem.isLiked == newItem.isLiked
        }
    }
}