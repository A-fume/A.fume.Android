package com.afume.afume_android.data.vo.request

import android.os.Parcelable
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import kotlinx.parcelize.Parcelize

data class RequestSearch(
    val searchText:String="",
    val keywordList:MutableList<Int>?,
    val ingredientList:MutableList<Int>?,
    val brandList:MutableList<Int>?
)

@Parcelize
data class SendFilter(
    val filterInfoPList: MutableList<FilterInfoP>?
) : Parcelable

//type ingredient = 1 , brand = 2, keyword = 3
@Parcelize
data class FilterInfoP(
    val idx: Int,
    val name: String="",
    var type: Int
):Parcelable

