package com.afume.afume_android.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.FilterRepository
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.SendFilter
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
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
    val selectedSeriesMap: MutableLiveData<MutableMap<String, List<SeriesIngredients>>> =
        MutableLiveData()

    //brand List
    var brandMap: MutableLiveData<MutableMap<String, MutableList<BrandInfo>>> =
        MutableLiveData(mutableMapOf())
    val selectedBrandMap: MutableLiveData<MutableList<BrandInfo>> = MutableLiveData()

    init {
        Log.e("filter", "생성됐다")
        getBrand()
        getSeries()
        getKeyword()

        badgeCount.value = mutableListOf(0, 0, 0)

    }

    fun blockClickSeriesMoreThan5() {
        val temp = _seriesList.value
        Log.e("count badge 1 brand", badgeCount.value?.get(0).toString())
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
            Log.e("count badge 1 brand", badgeCount.value?.get(1).toString())
            tempMap?.forEach {
                val brandInitial = it
                brandInitial.value.forEach { brand ->
                    brand.clickable = false
                    selectedBrandMap.value?.forEach {
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
            Log.e("count", badgeCount.value?.get(2).toString())
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

    fun addSeriesIngredientIdx(series: String, idxList: List<SeriesIngredients>) {
        var tempMap = mutableMapOf<String, List<SeriesIngredients>>()

        if (selectedSeriesMap.value != null) {
            tempMap = selectedSeriesMap.value!!

            if (tempMap.containsKey(series)) {
                tempMap.remove(series)
            }
        }
        tempMap[series] = idxList

        selectedSeriesMap.value = tempMap
        Log.e("선택된 계열은", series + " 선택된 ingredient    " + selectedSeriesMap.value)
    }

    fun setSelectedBrandListIdx(brand: BrandInfo, add: Boolean) {
        var tempBrandList = mutableListOf<BrandInfo>()

        if (selectedBrandMap.value != null) {
            tempBrandList = selectedBrandMap.value!!
        }

        if (add) tempBrandList.add(brand)
        else tempBrandList.remove(brand)

        selectedBrandMap.value = tempBrandList
        Log.e("선택된 브랜드 리스트는", selectedBrandMap.value.toString())
    }

    fun countBadges(index: Int, add: Boolean) {
        var tempBadgeCount = mutableListOf<Int>()

        if (badgeCount.value != null) {
            tempBadgeCount = badgeCount.value!!

            if (add) tempBadgeCount[index] = tempBadgeCount[index] + 1
            else {
                Log.e("뱃지 - 할거다 ", tempBadgeCount.toString())
                if (tempBadgeCount[index] >= 1) tempBadgeCount[index] = tempBadgeCount[index] - 1
            }

            badgeCount.value = tempBadgeCount

        }
        // todo index가 3 이상이면 에러 날리기
        var allCount = 0
        badgeCount.value?.forEach { allCount += it }
//        Log.e("allcount",allCount.toString())
        applyBtn.value = allCount

    }


    fun addKeywordList(keyword: KeywordInfo, add: Boolean) {
        if (add) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(keyword)) tempSelectedKeywordList.add(keyword)
            selectedKeywordList.value = tempSelectedKeywordList

            Log.e("index", keyword.toString())
            Log.e("add keyword", selectedKeywordList.value.toString())
        } else {
            selectedKeywordList.value?.remove(keyword)

            Log.e("index", keyword.toString())
            Log.e("remove keyword", selectedKeywordList.value.toString())
        }
    }

    fun getBrand() {
        viewModelScope.launch {
            try {
                val initialBrand = filterRepository.getBrand()
                Log.e("getBrand", initialBrand.toString())

                val tempMap = mutableMapOf<String, MutableList<BrandInfo>>()
                initialBrand.forEach {
                    tempMap.put(it.firstInitial, it.brands)
                }
                brandMap.value = tempMap
            } catch (e: HttpException) {

            }
        }

    }

    fun getSeries() {
        viewModelScope.launch {
            try {
                val series = filterRepository.getSeries().rows
                series.forEach {
                    it.ingredients.forEach { ingredients->
                        ingredients.seriesName=it.name
                    }
                    val entireIngredients =
                        SeriesIngredients(ingredientIdx = -1, name = it.name+" 전체", seriesName = it.name)
                    it.ingredients.add(0, entireIngredients)
                }
                Log.e("series 통신", series.toString())
                _seriesList.value = series
                Log.e("series 통신 후 라이브데이터에 전달", _seriesList.value.toString())
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

            it.value.forEach { it ->
                var ingredientInfoP = FilterInfoP(it.ingredientIdx, it.name, 1)
                filterInfoPList.add(ingredientInfoP)
            }
        }

        selectedBrandMap.value?.forEach {
            val brandInfoP = FilterInfoP(it.brandIdx, it.name, 2)
            filterInfoPList.add(brandInfoP)
        }

        selectedKeywordList.value?.forEach {
            val keywordInfoP = FilterInfoP(it.keywordIdx, it.name, 3)
            filterInfoPList.add(keywordInfoP)
        }

        return SendFilter(filterInfoPList)
    }

    companion object {
        private var instance: FilterViewModel? = null
        fun getInstance() = instance ?: synchronized(FilterViewModel::class.java) {
            instance ?: FilterViewModel().also { instance = it }
        }
    }

}