package com.afume.afume_android.data.vo.request

import android.os.Parcelable
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import kotlinx.parcelize.Parcelize


@Parcelize
data class RequestSearch(
    val filterInfoPList: MutableList<FilterInfoP>?
) : Parcelable

//type ingredient = 1 , brand = 2, keyword = 3
@Parcelize
data class FilterInfoP(
    val idx: Int,
    val name: String="",
    var type: Int
):Parcelable

