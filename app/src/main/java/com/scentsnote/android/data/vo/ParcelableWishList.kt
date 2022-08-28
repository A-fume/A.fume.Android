package com.scentsnote.android.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableWishList(
    val perfumeIdx: Int,
    val reviewIdx: Int,
    val perfumeName: String,
    val brandName: String,
    val imageUrl: String?
) : Parcelable
