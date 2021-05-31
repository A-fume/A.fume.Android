package com.afume.afume_android.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SearchRepository
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.RequestSearch
import com.afume.afume_android.data.vo.response.PerfumeInfo
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel(){
    private val searchRepository = SearchRepository()

    val filterList = MutableLiveData(mutableListOf<FilterInfoP>())
    val perfumeList= MutableLiveData(mutableListOf<PerfumeInfo>())

    init {
        filterList.value = mutableListOf()
        postSearchResultPerfume()
    }

    fun postSearchResultPerfume(){
        val requestSearch = RequestSearch("",mutableListOf<Int>(),mutableListOf<Int>(),mutableListOf<Int>())
        filterList.value?.forEach {
            when(it.type){
                1-> requestSearch.ingredientList?.add(it.idx)
                2->requestSearch.brandList?.add(it.idx)
                3->requestSearch.keywordList?.add(it.idx)
            }
        }
        Log.e("Request Search ",requestSearch.toString())

        viewModelScope.launch {
            perfumeList.value=searchRepository.postResultPerfume(requestSearch)
            Log.e("search result", perfumeList.value.toString())}

    }



}