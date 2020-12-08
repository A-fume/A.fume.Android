package com.afume.afume_android.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterViewModel :ViewModel(){
    private val _incenseBadgeCount = MutableLiveData<Int>(0)
    val incenseBadgeCount:LiveData<Int> get()=_incenseBadgeCount
    private val _brandBadgeCount = MutableLiveData<Int>(0)
    val brandBadgeCount:LiveData<Int> get()=_brandBadgeCount
    private val _keywordBadgeCount = MutableLiveData<Int>(0)
    val keywordBadgeCount:LiveData<Int> get()=_keywordBadgeCount

    private val _applyBtn = MutableLiveData<Int>(0)
    val applyBtn:LiveData<Int> get()=_applyBtn

    fun increaseBadgeCount(index:Int){
        when(index){
            0->{ _incenseBadgeCount.value =(incenseBadgeCount.value?:0)+1 }
            1->{ _brandBadgeCount.value =(brandBadgeCount.value?:0)+1 }
            2->{ _keywordBadgeCount.value =(keywordBadgeCount.value?:0)+1 }
        }
        _applyBtn.value = (_incenseBadgeCount.value?:0) + (_brandBadgeCount.value?:0) + (_keywordBadgeCount.value?:0)
    }
    fun decreaseBadgeCount(index:Int){
        when(index){
            0->{ if(incenseBadgeCount.value?:0 > 0) _incenseBadgeCount.value = (incenseBadgeCount.value?:0)-1 }
            1->{ if(brandBadgeCount.value?:0 > 0) _brandBadgeCount.value =(brandBadgeCount.value?:0)-1 }
            2->{ if(keywordBadgeCount.value?:0 > 0) _keywordBadgeCount.value =(keywordBadgeCount.value?:0)-1 }
        }
        _applyBtn.value = (_incenseBadgeCount.value?:0) + (_brandBadgeCount.value?:0) + (_keywordBadgeCount.value?:0)
    }

}