package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.data.vo.response.PerfumeInfo

class SurveyViewModel: ViewModel(){



    private val selectedPerfumeListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    private var selectedPerfumeList = ArrayList<Int>()

    private val _keywordListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val keywordListLiveData: LiveData<ArrayList<Int>> get() = _keywordListLiveData
    private var selectedKeywordList = ArrayList<Int>()

    private val _seriesListLiveData: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val seriesListLiveData: LiveData<ArrayList<Int>> get() = _seriesListLiveData
    private var selectedSeriesList = ArrayList<Int>()

    private  val _perfumeList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val perfumeList: LiveData<MutableList<PerfumeInfo>> get() = _perfumeList

    private  val _seriesList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<PerfumeInfo>> get() = _seriesList

    fun addPerfumeList(index:Int){
//        if(selectedPerfumeListLiveData.value==null) temp.clear()
//        else temp =selectedPerfumeListLiveData.value!!
        if(selectedPerfumeListLiveData.value!=null) selectedPerfumeList =selectedPerfumeListLiveData.value!!
        if(!selectedPerfumeList.contains(index))selectedPerfumeList.add(index)
        selectedPerfumeListLiveData.value=selectedPerfumeList


        Log.e("index",index.toString())
        Log.e("add perfume", selectedPerfumeListLiveData.value.toString())
    }
    fun removePerfumeList(index:Int){
        selectedPerfumeListLiveData.value?.remove(index)
        Log.e("index",index.toString())
        Log.e("remove perfume", selectedPerfumeListLiveData.value.toString())
    }

    fun setPerfumeList(list:MutableList<PerfumeInfo>){
        _perfumeList.value=list
        Log.e("setPerfumeList",list.toString())
        Log.e("setPerfumeList",_perfumeList.value.toString())
    }

    fun addKeywordList(index:Int){
        if(keywordListLiveData.value==null) selectedKeywordList.clear()
        else selectedKeywordList = keywordListLiveData.value!!
        if(!selectedKeywordList.contains(index)) selectedKeywordList.add(index)
        _keywordListLiveData.value=selectedKeywordList

        Log.e("index",index.toString())
        Log.e("add keyword", _keywordListLiveData.value.toString())
    }
    fun removeKeywordList(index:Int){
        _keywordListLiveData.value?.remove(index)

        Log.e("index",index.toString())
        Log.e("remove keyword", _keywordListLiveData.value.toString())
    }

    fun addSeriesList(index: Int){
        if(seriesListLiveData.value==null) selectedSeriesList.clear()
        else selectedSeriesList = seriesListLiveData.value!!
        if(!selectedSeriesList.contains(index))selectedSeriesList.add(index)
        _seriesListLiveData.value=selectedSeriesList

        Log.e("index",index.toString())
        Log.e("add series", _seriesListLiveData.value.toString())
    }
    fun removeSeriesList(index:Int){
        _seriesListLiveData.value?.remove(index)
        Log.e("index",index.toString())
        Log.e("remove series", seriesListLiveData.value.toString())
    }



}