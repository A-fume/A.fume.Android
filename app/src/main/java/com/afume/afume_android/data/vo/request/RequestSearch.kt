package com.afume.afume_android.data.vo.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestSearch(
    val keywordList: MutableList<Int>?,
    val ingredientList: MutableList<Int>?,
    val brandList: MutableList<Int>?
) : Parcelable
