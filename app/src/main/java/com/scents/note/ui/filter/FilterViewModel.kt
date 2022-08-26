package com.scents.note.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scents.note.data.repository.FilterRepository
import com.scents.note.data.vo.request.FilterInfoP
import com.scents.note.data.vo.request.SendFilter
import com.scents.note.data.vo.response.BrandInfo
import com.scents.note.data.vo.response.KeywordInfo
import com.scents.note.data.vo.response.SeriesInfo
import com.scents.note.data.vo.response.SeriesIngredients
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FilterViewModel : ViewModel() {
    private val filterRepository = FilterRepository()

    // badge count
    val badgeCount = MutableLiveData<MutableList<Int>>()
    val applyBtn = MutableLiveData<Int>(0)


    // selected List - keyword
    val selectedKeywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<KeywordInfo>()

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    //series List
    var _seriesList: MutableLiveData<MutableList<SeriesInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<SeriesInfo>> get() = _seriesList

    // selected List - series
    val selectedSeriesMap: MutableLiveData<MutableMap<String, MutableList<SeriesIngredients>>> =
        MutableLiveData()

    //brand List
    var brandMap: MutableLiveData<MutableMap<String, MutableList<BrandInfo>>> =
        MutableLiveData(mutableMapOf())
    var brandTabOrders: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val selectedBrandList: MutableLiveData<MutableList<BrandInfo>> = MutableLiveData()

    init {
        getBrand()
        getSeries()
        getKeyword()
        badgeCount.value = mutableListOf(0, 0, 0)
    }

    fun initFilterData() {
        //여기 통신 말고 local에서 data click 해제 해야함.
        getBrand()
        getSeries()
        getKeyword()

        selectedKeywordList.value = mutableListOf()
        selectedSeriesMap.value = mutableMapOf()
        selectedBrandList.value = mutableListOf()

        badgeCount.value = mutableListOf(0, 0, 0)
    }

    fun blockClickSeriesMoreThan5() {
        val temp = _seriesList.value
        Log.d("count badge 1 brand", badgeCount.value?.get(0).toString())
        temp?.forEach {
            val ingredients = it.ingredients
            ingredients.forEach { ingredient ->
                if (badgeCount.value?.get(0)!! >= 5) {
                    ingredient.clickable = false
                    selectedSeriesMap.value?.forEach {
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

    fun blockClickBrandMoreThan5() {
        val tempMap = brandMap.value
        if (badgeCount.value?.get(1)!! >= 5) {
            Log.d("count badge 1 brand", badgeCount.value?.get(1).toString())
            tempMap?.forEach {
                val brandInitial = it
                brandInitial.value.forEach { brand ->
                    brand.clickable = false
                    selectedBrandList.value?.forEach {
                        if (brand.brandIdx == it.brandIdx) brand.clickable = true
                    }
                }
            }
        } else {
            tempMap?.forEach {
                it.value.forEach { it.clickable = true }
            }
        }
        brandMap.value = tempMap
    }

    fun blockClickKeywordMoreThan5() {
        val tempList = keywordList.value
        if (badgeCount.value?.get(2)!! >= 5) {
            Log.d("count", badgeCount.value?.get(2).toString())
            tempList?.forEach {
                val keyword = it
                keyword.clickable = false
                selectedKeywordList.value?.forEach {
                    if (keyword.keywordIdx == it.keywordIdx) keyword.clickable = true
                }
            }
        } else {
            tempList?.forEach {
                it.clickable = true
            }
        }
        _keywordList.value = tempList
    }

    fun bindBrandTab(initial: String): MutableList<BrandInfo> {
        return brandMap.value!![initial] ?: mutableListOf()
    }

    fun addSeriesIngredientIdx(series: String, idxList: MutableList<SeriesIngredients>) {
        var tempMap = mutableMapOf<String, MutableList<SeriesIngredients>>()

        if (selectedSeriesMap.value != null) {
            tempMap = selectedSeriesMap.value!!

            if (tempMap.containsKey(series)) {
                tempMap.remove(series)
            }
        }
        tempMap[series] = idxList

        selectedSeriesMap.value = tempMap
        Log.d("선택된 계열은", series + " 선택된 ingredient    " + selectedSeriesMap.value)
    }

    fun setSelectedBrandListIdx(brand: BrandInfo, add: Boolean) {
        var tempBrandList = mutableListOf<BrandInfo>()

        if (selectedBrandList.value != null) {
            tempBrandList = selectedBrandList.value!!
        }

        if (add) tempBrandList.add(brand)
        else tempBrandList.remove(brand)

        selectedBrandList.value = tempBrandList
        Log.d("선택된 브랜드 리스트는", selectedBrandList.value.toString())
    }

    fun countBadges(index: Int, add: Boolean) {

        if (badgeCount.value != null) {
            val tempBadgeCount = badgeCount.value!!

            if (add) tempBadgeCount[index] = tempBadgeCount[index] + 1
            else {
                Log.d("뱃지 - 할거다 ", tempBadgeCount.toString())
                if (tempBadgeCount[index] >= 1) tempBadgeCount[index] = tempBadgeCount[index] - 1
            }

            badgeCount.value = tempBadgeCount

        }
        getTotalBadgeCount()

    }

    fun getTotalBadgeCount() {
        var allCount = 0
        badgeCount.value?.forEach { allCount += it }
        applyBtn.value = allCount
    }

    fun addKeywordList(keyword: KeywordInfo, boolean: Boolean) {
        if (boolean) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(keyword)) tempSelectedKeywordList.add(keyword)
            selectedKeywordList.value = tempSelectedKeywordList

            Log.d("index", keyword.toString())
            Log.d("add keyword", selectedKeywordList.value.toString())
        } else {
            selectedKeywordList.value?.remove(keyword)

            Log.d("index", keyword.toString())
            Log.d("remove keyword", selectedKeywordList.value.toString())
        }
        clickFilterKeywordList(_keywordList,keyword.keywordIdx,boolean)
    }

    private fun clickFilterKeywordList(keywordList: MutableLiveData<MutableList<KeywordInfo>>, keywordIdx: Int, isSelected:Boolean){
        val tempList = keywordList.value
        tempList?.forEach { if(it.keywordIdx==keywordIdx) it.checked= isSelected}
        keywordList.value=tempList
    }

    fun getBrand() {
        viewModelScope.launch {
            try {
                val initialBrand = filterRepository.getBrand()
                Log.d("getBrand", initialBrand.toString())

                val tempMap = mutableMapOf<String, MutableList<BrandInfo>>()
                val mapOrders = mutableListOf<String>()
                initialBrand.forEach {
                    tempMap.put(it.firstInitial, it.brands)
                    mapOrders.add(it.firstInitial)
                }
                brandMap.value = tempMap
                brandTabOrders.value = mapOrders.sorted().toMutableList()
                Log.d("getBrand", brandMap.value.toString())
                Log.d("getBrand", brandTabOrders.value.toString())
            } catch (e: HttpException) {
                Log.d("getBrand exception", brandMap.value.toString())
            }
        }

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
                        SeriesIngredients(
                            ingredientIdx = -1*it.seriesIdx,
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
        selectedSeriesMap.value?.mapValues {
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

        selectedBrandList.value?.forEach {
            val brandInfoP = FilterInfoP(it.brandIdx, it.name, 2)
            filterInfoPList.add(brandInfoP)
        }

        selectedKeywordList.value?.forEach {
            val keywordInfoP = FilterInfoP(it.keywordIdx, it.name, 3)
            filterInfoPList.add(keywordInfoP)
        }

        return SendFilter(filterInfoPList, selectedSeriesMap.value)
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
        selectedSeriesMap.value = changeFilter?.filterSeriesPMap

        // change values of selected lists and badges from search result view
        selectedSeriesMap.value = changeFilter?.filterSeriesPMap
        badgeCount.value?.set(0, seriesCount)
        selectedBrandList.value = brand
        badgeCount.value?.set(1, brand.size)
        selectedKeywordList.value = keyword
        badgeCount.value?.set(2, keyword.size)

        Log.d("change filter", selectedSeriesMap.value.toString())

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

        // 브랜드
        brandMap.value?.values?.forEach { list ->
            list.forEach { b ->
                b.check = false
                brand.forEach {
                    if (b.brandIdx == it.brandIdx) b.check = true
                }
            }
        }

        // 계열
        _seriesList.value?.forEach { series ->
            series.ingredients.forEach { s ->
                s.checked = false
                changeFilter?.filterSeriesPMap?.values?.forEach { list ->
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