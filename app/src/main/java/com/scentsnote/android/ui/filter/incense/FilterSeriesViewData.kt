package com.scentsnote.android.ui.filter.incense

import androidx.recyclerview.widget.DiffUtil
import com.scentsnote.android.data.vo.response.SeriesInfo
import com.scentsnote.android.utils.extension.isAllType

sealed class FilterSeriesViewData(
    open val index: Int,
    open val name: String,
    open var isChecked: Boolean = false
) {
    data class FilterSeriesAllType(
        override val index: Int,
        override val name: String,
        override var isChecked: Boolean = false,
        val ingredientIndices: List<Int>,
    ) : FilterSeriesViewData(index, name, isChecked) {
        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<FilterSeriesAllType>() {
                override fun areItemsTheSame(
                    oldItem: FilterSeriesAllType,
                    newItem: FilterSeriesAllType
                ): Boolean =
                    oldItem.index == newItem.index

                override fun areContentsTheSame(
                    oldItem: FilterSeriesAllType,
                    newItem: FilterSeriesAllType
                ): Boolean =
                    oldItem == newItem
            }
        }
    }

    data class FilterSeriesIngredient(
        override val index: Int,
        override val name: String,
        override var isChecked: Boolean = false,
        val seriesIndex: Int
    ) : FilterSeriesViewData(index, name, isChecked)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FilterSeriesViewData>() {
            override fun areItemsTheSame(
                oldItem: FilterSeriesViewData,
                newItem: FilterSeriesViewData
            ): Boolean =
                oldItem.isAllType == newItem.isAllType &&
                        oldItem.index == newItem.index

            override fun areContentsTheSame(
                oldItem: FilterSeriesViewData,
                newItem: FilterSeriesViewData
            ): Boolean =
                oldItem == newItem
        }
    }
}