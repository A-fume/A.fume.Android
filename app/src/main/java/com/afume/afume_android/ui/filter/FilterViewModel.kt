package com.afume.afume_android.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.FilterRepository
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.RequestSearch
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import kotlinx.coroutines.launch

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
    val selectedSeriesMap: MutableLiveData<MutableMap<Int, List<SeriesIngredients>>> = MutableLiveData()

    //brand List
    var brandMap: MutableLiveData<MutableMap<String, MutableList<BrandInfo>>> = MutableLiveData(mutableMapOf())
    val selectedBrandMap: MutableLiveData<MutableList<BrandInfo>> = MutableLiveData()

    init {
        viewModelScope.launch {
            _keywordList.value = filterRepository.getKeyword()
            getSeries()
            getBrand()
        }

        badgeCount.value = mutableListOf(0, 0, 0)

    }

    fun bindBrandTab(initial: String): MutableList<BrandInfo> {
        return brandMap.value!![initial] ?: mutableListOf()
    }

    suspend fun getBrand() {
        val initialBrand = filterRepository.getBrand()
        Log.e("getBrand", initialBrand.toString())

        val tempMap = mutableMapOf<String,MutableList<BrandInfo>>()
        initialBrand.forEach {
            tempMap.put(it.firstInitial, it.brands)
        }
        brandMap.value = tempMap
    }

    suspend fun getSeries(){
        val series = filterRepository.getSeries().rows
        series.forEach {
            val entireIngredients=SeriesIngredients(ingredientIdx = -1,name="전체",seriesIdx = it.seriesIdx)
            it.ingredients.add(0,entireIngredients)
        }
        Log.e("series 통신",series.toString())
        _seriesList.value=series
        Log.e("series 통신 후 라이브데이터에 전달", _seriesList.value.toString())
    }

    fun addSeriesIngredientIdx(seriesNumber: Int, idxList: List<SeriesIngredients>) {
        var tempMap = mutableMapOf<Int, List<SeriesIngredients>>()

        if (selectedSeriesMap.value != null) {
            tempMap = selectedSeriesMap.value!!

            if (tempMap.containsKey(seriesNumber)) {
                tempMap.remove(seriesNumber)
            }
        }
        tempMap[seriesNumber] = idxList

        selectedSeriesMap.value = tempMap
        Log.e("선택된 계열은", seriesNumber.toString() + " 선택된 ingredient    " + selectedSeriesMap.value)
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

    fun sendSelectFilter():RequestSearch{

        val filterInfoPList = mutableListOf<FilterInfoP>()
        selectedSeriesMap.value?.mapValues {
            it.value.forEach { it ->
                var ingredientInfoP=FilterInfoP(it.ingredientIdx,it.name,1)
                filterInfoPList.add(ingredientInfoP)
            }
        }

        selectedBrandMap.value?.forEach {
            val brandInfoP=FilterInfoP(it.brandIdx,it.name,2)
            filterInfoPList.add(brandInfoP)
        }

        selectedKeywordList.value?.forEach {
            val keywordInfoP=FilterInfoP(it.keywordIdx,it.name,3)
            filterInfoPList.add(keywordInfoP)
        }

        return RequestSearch(filterInfoPList)
    }

}