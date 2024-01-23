package com.scentsnote.android.viewmodel.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.data.repository.FilterRepository
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.FilterType
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.data.vo.response.BrandTab
import com.scentsnote.android.utils.etc.Log
import com.scentsnote.android.utils.extension.removeBrand
import com.scentsnote.android.utils.extension.resetBrand
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FilterBrandViewModel(
    private val filterRepository: FilterRepository = FilterRepository()
) : ViewModel() {
    val count: Int
        get() = selectedCount.value ?: 0

    val brandMap: LiveData<MutableMap<String, MutableList<BrandInfo>>>
        get() = _brandMap
    val brandTabOrders: LiveData<MutableList<String>>
        get() = _brandTabOrders
    val brandTabList: LiveData<MutableList<BrandTab>>
        get() = _brandTabList
    val selectedCount: LiveData<Int>
        get() = _selectedCount

    private val _brandMap: MutableLiveData<MutableMap<String, MutableList<BrandInfo>>> =
        MutableLiveData(mutableMapOf())
    private val _brandTabOrders: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val _brandTabList: MutableLiveData<MutableList<BrandTab>> = MutableLiveData()
    private val _selectedCount = MutableLiveData(0)

    private val selectedBrandList = mutableListOf<BrandInfo>()

    init {
        fetchBrands()
    }

    fun isOverSelectLimit(): Boolean =
        (selectedCount.value ?: 0) >= MAX_COUNT

    fun setSelectedBrandListIdx(brand: BrandInfo, isAdd: Boolean) {
        if (isAdd) {
            selectedBrandList.add(brand)
        } else {
            selectedBrandList.remove(brand)
        }
        _selectedCount.value = selectedBrandList.count()
    }

    fun bindBrandTab(initial: String): MutableList<BrandInfo> {
        return brandMap.value?.get(initial) ?: mutableListOf()
    }

    fun getSelectedBrands(): List<FilterInfoP> {
        return selectedBrandList.map { FilterInfoP(it.brandIdx, it.name, FilterType.Brand) }
    }

    fun getSelectedBrandsName() : String{
        return selectedBrandList.map { it.name }.toString()
    }

    fun clearSelectedList() {
        selectedBrandList.clear()
        _selectedCount.value = selectedBrandList.size
    }

    fun removeFromSelectedList(filterInfoP: FilterInfoP) {
       selectedBrandList.removeBrand(BrandInfo(filterInfoP.idx, filterInfoP.name))
        _selectedCount.value = selectedBrandList.size
    }

    fun resetSelectedBrandList(){
        selectedBrandList.resetBrand()
        clearSelectedList()
    }

    private fun fetchBrands() {
        viewModelScope.launch {
            try {
                val brandInitials = filterRepository.getBrand()
                val tempMap = mutableMapOf<String, MutableList<BrandInfo>>()
                val mapOrders = mutableListOf<String>()
                brandInitials.forEach {
                    tempMap[it.firstInitial] = it.brands
                    mapOrders.add(it.firstInitial)
                }
                _brandMap.value = tempMap
                _brandTabOrders.value = mapOrders.sorted().toMutableList()
                setBrandTab()
            } catch (e: HttpException) {
                Log.e(TAG, e.stackTraceToString())
            }
        }
    }

    fun setBrandTab(){
        val tempTab = mutableListOf<BrandTab>()
        _brandTabOrders.value?.forEach {
            tempTab.add(BrandTab(it, false))
        }
        tempTab[0].isSelected = true
        _brandTabList.value = tempTab
    }

    companion object {
        private const val MAX_COUNT = 5
        private const val TAG = "BrandViewModel"
    }
}