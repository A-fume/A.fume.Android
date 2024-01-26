package com.scentsnote.android.data.vo.request

import android.os.Parcelable
import com.scentsnote.android.data.vo.response.SeriesIngredient
import kotlinx.parcelize.Parcelize

data class RequestSearch(
    var searchText:String="",
    val keywordList:MutableList<Int>?,
    val ingredientList:MutableList<Int>?,
    val brandList:MutableList<Int>?
)

@Parcelize
data class SendFilter(
    var filterInfoPList: MutableList<FilterInfoP>?,
    var filterSeriesPMap: MutableMap<String,MutableList<SeriesIngredient>>?
) : Parcelable

@Parcelize
data class FilterInfoP(
    val idx: Int,
    val name: String="",
    var type: FilterType
):Parcelable

enum class FilterType{
    Ingredient, Brand, Keyword, Text
}

