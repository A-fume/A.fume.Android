package com.scents.note.data.vo.response

data class ResponseMyPerfume(
    val reviewIdx: Int,
    val score: Float,
    val perfumeIdx: Int,
    val perfumeName: String,
    val imageUrl: String,
    val brandIdx: String,
    val brandName: String
)