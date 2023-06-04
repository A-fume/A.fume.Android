package com.scentsnote.android.utils.extension

import com.scentsnote.android.ui.filter.incense.FilterSeriesViewData

internal val FilterSeriesViewData.isAllType: Boolean
    get() = this is FilterSeriesViewData.FilterSeriesAllType

internal fun MutableList<FilterSeriesViewData>.removeSeriesAllType(seriesViewData: FilterSeriesViewData) {
    val series = this.find {
        it.isAllType == seriesViewData.isAllType && it.index == seriesViewData.index
    }
    this.remove(series)
}

internal fun MutableList<FilterSeriesViewData>.removeSeries(seriesViewData: FilterSeriesViewData) {
    val series = this.find {
        it.index == seriesViewData.index && it.name == seriesViewData.name
    }
    this.remove(series)
}

internal fun FilterSeriesViewData.copy(
): FilterSeriesViewData {
    return when (this) {
        is FilterSeriesViewData.FilterSeriesAllType -> {
            copy()
        }

        is FilterSeriesViewData.FilterSeriesIngredient -> {
            copy()
        }
    }
}

internal fun FilterSeriesViewData.copy(
    isChecked: Boolean
): FilterSeriesViewData {
    return when (this) {
        is FilterSeriesViewData.FilterSeriesAllType -> {
            copy(isChecked = isChecked)
        }

        is FilterSeriesViewData.FilterSeriesIngredient -> {
            copy(isChecked = isChecked)
        }
    }
}

internal fun FilterSeriesViewData.isSameContent(other: FilterSeriesViewData): Boolean {
    return this.isAllType == other.isAllType && this.index == other.index
}