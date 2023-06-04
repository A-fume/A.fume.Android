package com.scentsnote.android.viewmodel.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.data.repository.FilterRepository
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.response.KeywordInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FilterKeywordViewModel(
    private val filterRepository: FilterRepository = FilterRepository()
) : ViewModel() {
    val count :Int
        get() = selectedCount.value?:0

    val selectedCount: LiveData<Int>
        get() = _selectedCount
    val keywordList: LiveData<MutableList<KeywordInfo>>
        get() = _keywordList

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    private val _selectedCount = MutableLiveData<Int>(0)

    private val selectedKeywordList = mutableListOf<KeywordInfo>()

    init {
        fetchKeywords()
    }

    fun isOverSelectLimit(): Boolean =
        (selectedCount.value ?: 0) >= MAX_COUNT

    fun selectKeywordList(keyword: KeywordInfo, isSelected: Boolean) {
        if (isSelected) {
            selectedKeywordList.add(keyword)
        } else {
            selectedKeywordList.remove(keyword)
        }
        _selectedCount.value = selectedKeywordList.count()
    }

    fun clearSelectedList(){
        selectedKeywordList.clear()
        _selectedCount.value = selectedKeywordList.size
    }

    private fun fetchKeywords() {
        viewModelScope.launch {
            try {
                _keywordList.value = filterRepository.getKeyword()
            } catch (e: HttpException) {

            }
        }
    }

    fun getSelectedKeywords(): List<FilterInfoP> {
        return selectedKeywordList.map { FilterInfoP(it.keywordIdx, it.name, 3) }
    }

    companion object {
        private const val MAX_COUNT = 5
    }
}