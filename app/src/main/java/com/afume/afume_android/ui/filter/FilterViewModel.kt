package com.afume.afume_android.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SearchRepository
import com.afume.afume_android.data.vo.response.BrandInfo
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import kotlinx.coroutines.launch

class FilterViewModel : ViewModel() {
    private val searchRepository = SearchRepository()

    // badge count
    val badgeCount = MutableLiveData<MutableList<Int>>()

    val applyBtn = MutableLiveData<Int>(0)


    // selected List - keyword
    val selectedKeywordList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<Int>()

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    //series List
    private var _seriesList: MutableLiveData<MutableList<SeriesInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<SeriesInfo>> get() = _seriesList

    // selected List - series
    val selectedSeriesMap: MutableLiveData<MutableMap<Int, List<Int>>> = MutableLiveData()

    //brand List
    private var _brandMap: MutableLiveData<MutableMap<String,MutableList<BrandInfo>>> = MutableLiveData()
    val brandMap: LiveData<MutableMap<String,MutableList<BrandInfo>>> get()=_brandMap

    val selectedBrandMap: MutableLiveData<MutableList<Int>> = MutableLiveData()

    init {
        viewModelScope.launch {
            _keywordList.value = searchRepository.getKeyword()
        }

        _seriesList = MutableLiveData(
            mutableListOf(
                SeriesInfo(
                    seriesIdx = 1,
                    ingredients = mutableListOf(
                        SeriesIngredients(name = "베르가못", ingredientIdx = 1, seriesIdx = 1),
                        SeriesIngredients(name = "오렌지", ingredientIdx = 2, seriesIdx = 1)
                    )
                ),
                SeriesInfo(
                    seriesIdx = 2,
                    name = "머스크",
                    ingredients = mutableListOf(
                        SeriesIngredients(name = "머스트1", ingredientIdx = 21, seriesIdx = 2),
                        SeriesIngredients(name = "머스트 2", ingredientIdx = 22, seriesIdx = 2)
                    )
                )
            )
        )
        badgeCount.value = mutableListOf(0, 0, 0)

        _brandMap= MutableLiveData(
            mutableMapOf(
                Pair("ㄱ", mutableListOf<BrandInfo>(
                    BrandInfo(brandIdx = 1,name="ㄱ1",firstInitial = "ㄱ"),
                    BrandInfo(brandIdx = 2,name="ㄱ2",firstInitial = "ㄱ"),
                    BrandInfo(brandIdx = 3,name="ㄱ3",firstInitial = "ㄱ"))
                ),
                Pair("ㄴ", mutableListOf<BrandInfo>(
                    BrandInfo(brandIdx = 21,name="ㄴ1",firstInitial = "ㄴ"),
                    BrandInfo(brandIdx = 22,name="ㄴ2",firstInitial = "ㄴ"),
                    BrandInfo(brandIdx = 23,name="ㄴ3",firstInitial = "ㄴ"))
                )
            )
        )

    }

    fun bindBrandTab(initial: String):MutableList<BrandInfo>{
        return brandMap.value!![initial] ?: mutableListOf()
    }


    fun addSeriesIngredientIdx(seriesNumber: Int, idxList: List<Int>) {
        var tempMap = mutableMapOf<Int, List<Int>>()

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

    fun setSelectedBrandListIdx(brandIdx:Int,add: Boolean){
        var tempBrandList = mutableListOf<Int>()

        if(selectedBrandMap.value != null){
            tempBrandList = selectedBrandMap.value!!
        }

        if(add) tempBrandList.add(brandIdx)
        else tempBrandList.remove(brandIdx)

        selectedBrandMap.value=tempBrandList
        Log.e("선택된 브랜드 리스트는",  selectedBrandMap.value.toString())
    }

    fun countBadges(index: Int, add: Boolean) {
        var tempBadgeCount= mutableListOf<Int>()

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


    fun addKeywordList(index: Int, add: Boolean) {
        if (add) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
            selectedKeywordList.value = tempSelectedKeywordList

            Log.e("index", index.toString())
            Log.e("add keyword", selectedKeywordList.value.toString())
        } else {
            selectedKeywordList.value?.remove(index)

            Log.e("index", index.toString())
            Log.e("remove keyword", selectedKeywordList.value.toString())
        }
    }
}