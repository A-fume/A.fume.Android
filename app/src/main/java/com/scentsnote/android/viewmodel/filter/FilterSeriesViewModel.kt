package com.scentsnote.android.viewmodel.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.data.repository.FilterRepository
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.ui.filter.incense.FilterSeriesViewData
import com.scentsnote.android.utils.extension.copy
import com.scentsnote.android.utils.extension.removeSeries
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FilterSeriesViewModel(
    private val filterRepository: FilterRepository = FilterRepository()
) : ViewModel() {
    val count: Int
        get() = selectedCount.value ?: 0

    private val seriesMap = mutableMapOf<Int, FilterSeriesViewData.FilterSeriesAllType>()
    private val ingredientMap = mutableMapOf<Int, FilterSeriesViewData.FilterSeriesIngredient>()
    private val selectedSeriesList = mutableListOf<FilterSeriesViewData>()

    val dataFetched: LiveData<Unit>
        get() = _dataFetched
    val selectedCount: LiveData<Int>
        get() = _selectedCount
    private val _dataFetched = MutableLiveData<Unit>()
    private val _selectedCount = MutableLiveData(0)

    init {
        fetchSeries()
    }

    fun isSelectedSeries(data: FilterSeriesViewData) = selectedSeriesList.contains(data)

    fun clearSelectedList() {
        selectedSeriesList.clear()
        _selectedCount.value = selectedSeriesList.size
    }

    private fun getParentSeries(ingredient: FilterSeriesViewData.FilterSeriesIngredient): FilterSeriesViewData.FilterSeriesAllType? {
        return seriesMap.values.find {
            it.ingredientIndices.contains(ingredient.index)
        }
    }

    fun onSelectAllType(
        data: FilterSeriesViewData.FilterSeriesAllType,
        newItems: MutableList<FilterSeriesViewData>,
        currentList: List<FilterSeriesViewData>
    ) {
        if (!data.isChecked) {
            newItems.add(data.copy(isChecked = true))
            data.ingredientIndices.forEach { index ->
                selectedSeriesList.find { it is FilterSeriesViewData.FilterSeriesIngredient && it.index == index }
            }
            currentList.forEach { filterSeriesViewData ->
                if (filterSeriesViewData is FilterSeriesViewData.FilterSeriesIngredient &&
                    filterSeriesViewData.isChecked
                ) {
                    newItems.add(filterSeriesViewData.copy(isChecked = false))
                }
            }
        }
        selectAllType(data, newItems)
    }

    private fun selectAllType(
        series: FilterSeriesViewData.FilterSeriesAllType,
        newItems: MutableList<FilterSeriesViewData>
    ) {
        if (!series.isChecked) {
            selectedSeriesList.add(series)
            seriesMap[series.index]?.ingredientIndices?.forEach { ingredientIdx ->
                ingredientMap[ingredientIdx]?.let { ingredient ->
                    selectedSeriesList.removeSeries(ingredient)
                }
            }
        } else {
            deselectSeries(series, newItems)
        }
        updateSelectCount()
    }

    fun onSelectIngredientType(
        data: FilterSeriesViewData.FilterSeriesIngredient,
        newItems: MutableList<FilterSeriesViewData>,
        parentData: FilterSeriesViewData? = null
    ) {
        if (!data.isChecked) {
            if (parentData?.isChecked == true) {
                newItems.add(parentData.copy(isChecked = false))
            }
            newItems.add(data.copy(isChecked = true))
            selectedSeriesList.add(data)
            getParentSeries(data)?.let { series ->
                selectedSeriesList.removeSeries(series)
            }

        } else {
            deselectSeries(data, newItems)
        }
        updateSelectCount()
    }

    private fun deselectSeries(
        data: FilterSeriesViewData,
        newItems: MutableList<FilterSeriesViewData>,
    ) {
        newItems.add(data.copy(isChecked = false))
        selectedSeriesList.removeSeries(data)
    }

    private fun updateSelectCount() {
        _selectedCount.value = selectedSeriesList.size
    }

    fun getFilterSeriesViewData(): List<FilterSeriesViewData.FilterSeriesAllType> {
        return seriesMap.values.toList()
    }

    fun getSelectedSeries(): List<FilterInfoP> {
        // todo: all type은 전체 담도록
        return selectedSeriesList.map { FilterInfoP(it.index, it.name, 1) }
    }

    fun getFilterSeriesIngredientViewData(seriesIdx: Int): List<FilterSeriesViewData> {
        val series = seriesMap[seriesIdx] ?: return emptyList()
        val ingredients = series.ingredientIndices.mapNotNull { ingredientMap[it] }
        val dataList = mutableListOf<FilterSeriesViewData>(
            series.copy(name = "${series.name} $SERIES_NAME_SUFFIX")
        )
        dataList.addAll(ingredients)
        return dataList
    }

    fun isOverSelectLimit(): Boolean =
        (selectedCount.value ?: 0) >= MAX_COUNT

    private fun fetchSeries() {
        viewModelScope.launch {
            try {
                val series = filterRepository.getSeries().rows
                series.forEach { seriesInfo ->
                    seriesMap[seriesInfo.seriesIdx] =
                        FilterSeriesViewData.FilterSeriesAllType(
                            seriesInfo.seriesIdx,
                            seriesInfo.name,
                            isChecked = false,
                            seriesInfo.ingredients.map { it.ingredientIdx }
                        )

                    seriesInfo.ingredients.forEach { ingredient ->
                        ingredientMap[ingredient.ingredientIdx] =
                            FilterSeriesViewData.FilterSeriesIngredient(
                                ingredient.ingredientIdx,
                                ingredient.name,
                                isChecked = false,
                                seriesInfo.seriesIdx
                            )
                    }
                }
                _dataFetched.value = Unit
            } catch (e: HttpException) {
                Log.d(TAG, "fetch series() failed: $e")
            }
        }
    }

    companion object {
        private const val MAX_COUNT = 5
        private const val TAG = "FilterSeriesViewModel"
        private const val SERIES_NAME_SUFFIX = "전체"
    }
}