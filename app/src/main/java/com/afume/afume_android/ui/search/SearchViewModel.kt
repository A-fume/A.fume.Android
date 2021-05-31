package com.afume.afume_android.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.data.vo.request.FilterInfoP

class SearchViewModel:ViewModel(){
    val filterList = MutableLiveData(mutableListOf<FilterInfoP>())

    init {
        filterList.value = mutableListOf(FilterInfoP(2, "시트러스", 2), FilterInfoP(3, "비누", 3))
    }


}