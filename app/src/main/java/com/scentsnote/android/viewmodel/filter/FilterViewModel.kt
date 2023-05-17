package com.scentsnote.android.viewmodel.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.data.repository.FilterRepository
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.data.vo.response.KeywordInfo
import com.scentsnote.android.data.vo.response.SeriesInfo
import com.scentsnote.android.data.vo.response.SeriesIngredient
import com.scentsnote.android.ui.filter.FilterCategory
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FilterViewModel : ViewModel() {
    private val filterRepository = FilterRepository()

    // badge count
    val applyBtn = MutableLiveData<Int>(0)
    val seriesCount: Int
        get() = _selectedSeriesCount.value ?: 0
    val brandCount: Int
        get() = _selectedBrandCount.value ?: 0
    val keywordCount: Int
        get() = _selectedKeywordCount.value ?: 0

    // selected List - keyword
    val selectedKeywordList = mutableListOf<KeywordInfo>()

    //    private var tempSelectedKeywordList = mutableListOf<KeywordInfo>()
    val selectedKeywordCount: LiveData<Int>
        get() = _selectedKeywordCount
    private val _selectedKeywordCount = MutableLiveData<Int>(0)

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    //series List
    var _seriesList: MutableLiveData<MutableList<SeriesInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<SeriesInfo>> get() = _seriesList

    // selected List - series
    val selectedSeriesMap = mutableMapOf<String, MutableList<SeriesIngredient>>()
    val selectedSeriesCount: LiveData<Int>
        get() = _selectedSeriesCount
    private val _selectedSeriesCount = MutableLiveData<Int>(0)

    val selectedBrandCount: LiveData<Int>
        get() = _selectedBrandCount
    private val _selectedBrandCount = MutableLiveData<Int>(0)

    init {
        initFilterData()
    }

    fun getSelectedCountLiveData(category: FilterCategory): LiveData<Int> {
        return when (category) {
            FilterCategory.Series -> selectedSeriesCount
            FilterCategory.Brand -> selectedBrandCount
            FilterCategory.Keyword -> selectedKeywordCount
        }
    }

    fun initFilterData() {
        //여기 통신 말고 local에서 data click 해제 해야함.
        getSeries()
        getKeyword()

        selectedKeywordList.clear()
        selectedSeriesMap.clear()
    }

    fun blockClickSeriesMoreThan5() {
        val temp = _seriesList.value
        temp?.forEach { seriesInfo ->
            val ingredients = seriesInfo.ingredients
            ingredients.forEach { ingredient ->
                if (seriesCount >= 5) {
                    ingredient.clickable = false
                    selectedSeriesMap.forEach {
                        it.value.forEach {
                            if (ingredient.ingredientIdx == it.ingredientIdx) ingredient.clickable =
                                true
                        }
                    }
                } else ingredient.clickable = true
            }
        }

        _seriesList.value = temp
    }


    fun blockClickKeywordMoreThan5() {
        val tempList = keywordList.value
        if (keywordCount >= 5) {
            tempList?.forEach {
                val keyword = it
                keyword.clickable = false
                selectedKeywordList.forEach { keywordInfo ->
                    if (keyword.keywordIdx == keywordInfo.keywordIdx) keyword.clickable = true
                }
            }
        } else {
            tempList?.forEach {
                it.clickable = true
            }
        }
        _keywordList.value = tempList
    }

    fun addSeriesIngredientIdx(series: String, idxList: MutableList<SeriesIngredient>) {
        selectedSeriesMap[series] = idxList
    }

    fun countBadges(index: Int, add: Boolean) {
        // TODO: remove it
    }

    private fun getTotalBadgeCount() {
        applyBtn.value = seriesCount + brandCount + keywordCount
    }

    fun addKeywordList(keyword: KeywordInfo, boolean: Boolean) {
        if (boolean) {
            if (!selectedKeywordList.contains(keyword)) selectedKeywordList.add(keyword)

            Log.d("index", keyword.toString())
            Log.d("add keyword", selectedKeywordList.toString())
            _selectedKeywordCount.value = (_selectedKeywordCount.value ?: 0) + 1
        } else {
            selectedKeywordList.remove(keyword)

            Log.d("index", keyword.toString())
            Log.d("remove keyword", selectedKeywordList.toString())
            _selectedKeywordCount.value = (_selectedKeywordCount.value ?: 0) - 1
        }
        clickFilterKeywordList(_keywordList, keyword.keywordIdx, boolean)
    }

    private fun clickFilterKeywordList(
        keywordList: MutableLiveData<MutableList<KeywordInfo>>,
        keywordIdx: Int,
        isSelected: Boolean
    ) {
        val tempList = keywordList.value
        tempList?.forEach { if (it.keywordIdx == keywordIdx) it.checked = isSelected }
        keywordList.value = tempList
    }

    fun getSeries() {
        viewModelScope.launch {
            try {
                val series = filterRepository.getSeries().rows
                series.forEach {
                    it.ingredients.forEach { ingredient ->
                        ingredient.seriesName = it.name
                        ingredient.clickable = true
                    }
                    val entireIngredients =
                        SeriesIngredient(
                            ingredientIdx = -1 * it.seriesIdx,
                            name = it.name + " 전체",
                            seriesName = it.name
                        )
                    it.ingredients.add(0, entireIngredients)
                }
                Log.d("series 통신", series.toString())
                _seriesList.value = series
                Log.d("series 통신 후 라이브데이터에 전달", _seriesList.value.toString())
            } catch (e: HttpException) {

            }
        }
    }

    fun getKeyword() {
        viewModelScope.launch {
            try {
                _keywordList.value = filterRepository.getKeyword()
            } catch (e: HttpException) {

            }
        }
    }

    fun sendSelectFilter(): SendFilter {

        val filterInfoPList = mutableListOf<FilterInfoP>()
        selectedSeriesMap.mapValues {
            if (it.value[0].ingredientIdx <= -1) {
                val ingredientInfoP = FilterInfoP(it.value[0].ingredientIdx, it.value[0].name, 1)
                filterInfoPList.add(ingredientInfoP)
            } else {
                it.value.forEach { ingredient ->
                    val ingredientInfoP = FilterInfoP(ingredient.ingredientIdx, ingredient.name, 1)
                    filterInfoPList.add(ingredientInfoP)
                }
            }
        }

        selectedKeywordList.forEach {
            val keywordInfoP = FilterInfoP(it.keywordIdx, it.name, 3)
            filterInfoPList.add(keywordInfoP)
        }

        return SendFilter(filterInfoPList, selectedSeriesMap)
    }

    fun checkChangeFilter(changeFilter: SendFilter?) {

        val brand = mutableListOf<BrandInfo>()
        val keyword = mutableListOf<KeywordInfo>()
        var seriesCount = 0

        changeFilter?.filterInfoPList?.forEach {
            when (it.type) {
                1 -> seriesCount++
                2 -> brand.add(BrandInfo(it.idx, it.name, true))
                3 -> keyword.add(KeywordInfo(it.name, it.idx, true))
            }
        }

        // change values of selected lists and badges from search result view
        selectedSeriesMap.clear()
        selectedSeriesMap.putAll(changeFilter?.filterSeriesPMap as Map<out String, MutableList<SeriesIngredient>>)
//        badgeCount.value?.set(0, seriesCount)
//        badgeCount.value?.set(1, brand.size)
        selectedKeywordList.clear()
        selectedKeywordList.addAll(keyword)
//        badgeCount.value?.set(2, keyword.size)

        Log.d("change filter", selectedSeriesMap.toString())

        // 뱃지 카운트 재정비
        getTotalBadgeCount()

        // view에 표시하기 위한 리스트
        // 키워드
        _keywordList.value?.forEach { k ->
            k.checked = false
            keyword.forEach {
                if (it.keywordIdx == k.keywordIdx) k.checked = true
            }
        }

        // 계열
        _seriesList.value?.forEach { series ->
            series.ingredients.forEach { s ->
                s.checked = false
                changeFilter.filterSeriesPMap?.values?.forEach { list ->
                    loop@ for (selected in list) {
                        if (s.ingredientIdx == selected.ingredientIdx) {
                            s.checked = true
                            if (selected.ingredientIdx <= -1) continue@loop
                            Log.d("change filter s", s.toString())
                        }
                    }
                }
            }
        }
        Log.d("change filter series", _seriesList.value.toString())

    }

    companion object {
        private var instance: FilterViewModel? = null
        fun getInstance() = instance ?: synchronized(FilterViewModel::class.java) {
            instance ?: FilterViewModel().also { instance = it }
        }
    }

}