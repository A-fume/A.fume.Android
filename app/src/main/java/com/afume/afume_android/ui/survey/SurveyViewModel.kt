package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyViewModel: ViewModel(){

    private var temp = ArrayList<Int>()
    private val _perfumeListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val perfumeListLiveData: LiveData<ArrayList<Int>> get() = _perfumeListLiveData

    private val _keywordListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val keywordListLiveData: LiveData<ArrayList<Int>> get() = _keywordListLiveData

    private val _seriesListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val seriesListLiveData: LiveData<ArrayList<Int>> get() = _seriesListLiveData

    fun addPerfumeList(index:Int){
        if(perfumeListLiveData.value!=null) temp = perfumeListLiveData.value!!
        else temp.clear()
        temp.add(index)
        _perfumeListLiveData.value=temp

        Log.e("index",index.toString())
        Log.e("add perfume", _perfumeListLiveData.value.toString())
    }
    fun removePerfumeList(index:Int){
        _perfumeListLiveData.value?.remove(index)
        Log.e("index",index.toString())
        Log.e("remove perfume", _perfumeListLiveData.value.toString())
    }

    fun addKeywordList(index:Int){
        if(keywordListLiveData.value!=null) temp = keywordListLiveData.value!!
        else temp.clear()
        temp.add(index)
        _keywordListLiveData.value=temp
    }
    fun removeKeywordList(index:Int){
        _keywordListLiveData.value?.remove(index)
    }

    fun addSeriesList(index: Int){

        if(seriesListLiveData.value!=null) temp = seriesListLiveData.value!!
        else temp.clear()
        temp.add(index)
        _seriesListLiveData.value=temp

        Log.e("index",index.toString())
        Log.e("add series", _seriesListLiveData.value.toString())
    }
    fun removeSeriesList(index:Int){
        _seriesListLiveData.value?.remove(index)
        Log.e("index",index.toString())
        Log.e("remove series", _keywordListLiveData.value.toString())
    }



}