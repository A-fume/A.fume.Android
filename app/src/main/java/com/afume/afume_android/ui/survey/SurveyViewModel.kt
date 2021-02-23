package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SurveyRepository
import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.SeriesInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {
    private val surveyRepository = SurveyRepository()

    private val selectedPerfumeListLiveData: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var selectedPerfumeList = mutableListOf<Int>()

    private val _keywordListLiveData: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val keywordListLiveData: LiveData<MutableList<Int>> get() = _keywordListLiveData
    private var selectedKeywordList = mutableListOf<Int>()

    private val _seriesListLiveData: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val seriesListLiveData: LiveData<MutableList<Int>> get() = _seriesListLiveData
    private var selectedSeriesList = mutableListOf<Int>()

    private val _perfumeList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val perfumeList: LiveData<MutableList<PerfumeInfo>> get() = _perfumeList

    private val _seriesList: MutableLiveData<MutableList<SeriesInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<SeriesInfo>> get() = _seriesList

    private val _keywordList: MutableLiveData<MutableList<ResponseKeyword>> = MutableLiveData()
    val keywordList: LiveData<MutableList<ResponseKeyword>> get() = _keywordList

    init {
        viewModelScope.launch {
            _seriesList.value=surveyRepository.getSeries()
            _perfumeList.value=surveyRepository.getSurveyPerfume()
            _keywordList.value=surveyRepository.getKeyword()
            Log.e("perfume value", _perfumeList.value.toString())
        }
    }

    fun addPerfumeList(index: Int) {
//        if(selectedPerfumeListLiveData.value==null) temp.clear()
//        else temp =selectedPerfumeListLiveData.value!!
        if (selectedPerfumeListLiveData.value != null) selectedPerfumeList =
            selectedPerfumeListLiveData.value!!
        if (!selectedPerfumeList.contains(index)) selectedPerfumeList.add(index)
        selectedPerfumeListLiveData.value = selectedPerfumeList


        Log.e("index", index.toString())
        Log.e("add perfume", selectedPerfumeListLiveData.value.toString())
    }

    fun removePerfumeList(index: Int) {
        selectedPerfumeListLiveData.value?.remove(index)
        Log.e("index", index.toString())
        Log.e("remove perfume", selectedPerfumeListLiveData.value.toString())
    }

    fun setPerfumeList(list: MutableList<PerfumeInfo>) {
        _perfumeList.value = list
        Log.e("setPerfumeList", list.toString())
        Log.e("setPerfumeList", _perfumeList.value.toString())
    }

    fun addKeywordList(index: Int) {
        if (keywordListLiveData.value == null) selectedKeywordList.clear()
        else selectedKeywordList = keywordListLiveData.value!!
        if (!selectedKeywordList.contains(index)) selectedKeywordList.add(index)
        _keywordListLiveData.value = selectedKeywordList

        Log.e("index", index.toString())
        Log.e("add keyword", _keywordListLiveData.value.toString())
    }

    fun removeKeywordList(index: Int) {
        _keywordListLiveData.value?.remove(index)

        Log.e("index", index.toString())
        Log.e("remove keyword", _keywordListLiveData.value.toString())
    }

    fun addSeriesList(index: Int) {
        if (seriesListLiveData.value == null) selectedSeriesList.clear()
        else selectedSeriesList = seriesListLiveData.value!!
        if (!selectedSeriesList.contains(index)) selectedSeriesList.add(index)
        _seriesListLiveData.value = selectedSeriesList

        Log.e("index", index.toString())
        Log.e("add series", _seriesListLiveData.value.toString())
    }

    fun removeSeriesList(index: Int) {
        _seriesListLiveData.value?.remove(index)
        Log.e("index", index.toString())
        Log.e("remove series", seriesListLiveData.value.toString())
    }

    fun postSurvey(token: String){
        viewModelScope.launch(Dispatchers.IO) {
                val message = surveyRepository.postSurvey(
                    token,
                    RequestSurvey(
                        keywordListLiveData.value,
                        seriesListLiveData.value,
                        selectedPerfumeListLiveData.value
                    )
                )
                Log.e("survey post",message)
        }
    }


}