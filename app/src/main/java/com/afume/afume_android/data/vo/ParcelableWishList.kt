package com.afume.afume_android.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableWishList(
    val perfumeIdx: Int,
    val perfumeName: String,
    val brandName: String,
    val imageUrl: String
) : Parcelable
