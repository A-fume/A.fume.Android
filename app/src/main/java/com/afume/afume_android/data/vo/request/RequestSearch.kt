package com.afume.afume_android.data.vo.request


import android.os.Parcelable
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import kotlinx.parcelize.Parcelize


@Parcelize
data class RequestSearch(
    val keywordList: MutableList<KeywordInfoP>?,
    val ingredientList: MutableList<IngredientInfoP>?,
    val brandList: MutableList<BrandInfoP>?
) : Parcelable

@Parcelize
data class KeywordInfoP(
    val keywordIdx: Int,
    val name: String="",
    var check: Boolean=false
):Parcelable

@Parcelize
data class IngredientInfoP(
    val ingredientIdx: Int,
    val name: String="",
    var check: Boolean=false
):Parcelable

@Parcelize
data class BrandInfoP(
    val brandIdx: Int,
    val name: String="",
    var check: Boolean=false
):Parcelable