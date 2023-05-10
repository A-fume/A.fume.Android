package com.scentsnote.android.data.vo.response

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

data class ResponseSeries(
    val count: Int,
    val rows: MutableList<SeriesInfo>
)

data class SeriesInfo(
    val seriesIdx: Int = 1,
    val name: String = "시트러스",
    val englishName: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val ingredients: MutableList<SeriesIngredient>,
    var isLiked: Boolean = false
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SeriesInfo>() {
            override fun areItemsTheSame(
                oldItem: SeriesInfo,
                newItem: SeriesInfo
            ): Boolean =
                oldItem.seriesIdx == newItem.seriesIdx

            override fun areContentsTheSame(
                oldItem: SeriesInfo,
                newItem: SeriesInfo
            ): Boolean =
                oldItem == newItem
        }
    }
}

@Parcelize
data class SeriesIngredient(
    val ingredientIdx: Int = 2,
    val name: String = "베르가못",
    var seriesName: String,
    var checked: Boolean = false,
    var clickable: Boolean = true
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SeriesIngredient>() {
            override fun areItemsTheSame(
                oldItem: SeriesIngredient,
                newItem: SeriesIngredient
            ): Boolean {
                return oldItem.ingredientIdx == newItem.ingredientIdx
            }

            override fun areContentsTheSame(
                oldItem: SeriesIngredient,
                newItem: SeriesIngredient
            ): Boolean {
                return oldItem.checked == newItem.checked
            }

        }
    }
}