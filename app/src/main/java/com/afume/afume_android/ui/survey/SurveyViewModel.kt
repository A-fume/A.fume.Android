package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SurveyRepository
import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {
    private val surveyRepository = SurveyRepository()

    private val selectedPerfumeList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedPerfumeList = mutableListOf<Int>()

    val selectedKeywordList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<Int>()

    val selectedSeriesList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedSeriesList = mutableListOf<Int>()

    private val _perfumeList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val perfumeList: LiveData<MutableList<PerfumeInfo>> get() = _perfumeList

    private val _seriesList: MutableLiveData<MutableList<SeriesInfo>> = MutableLiveData()
    val seriesList: LiveData<MutableList<SeriesInfo>> get() = _seriesList

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    init {
        viewModelScope.launch {
            _seriesList.value = surveyRepository.getSeries()
            _perfumeList.value = surveyRepository.getSurveyPerfume()
            _keywordList.value = surveyRepository.getKeyword()
            Log.e("perfume value", _perfumeList.value.toString())
            Log.e("mutavle", tempSelectedPerfumeList.toString())
            selectedPerfumeList.value = tempSelectedPerfumeList
            selectedKeywordList.value = tempSelectedKeywordList
            selectedSeriesList.value = tempSelectedSeriesList
        }
    }

    fun addPerfumeList(index: Int) {
        if (selectedPerfumeList.value != null) tempSelectedPerfumeList =
            selectedPerfumeList.value!!

        if (!tempSelectedKeywordList.contains(index)) tempSelectedPerfumeList.add(index)
        selectedPerfumeList.value = tempSelectedPerfumeList


        Log.e("index", index.toString())
        Log.e("add perfume", selectedPerfumeList.value.toString())
    }

    fun removePerfumeList(index: Int) {
        selectedPerfumeList.value?.remove(index)
        Log.e("index", index.toString())
        Log.e("remove perfume", selectedPerfumeList.value.toString())
    }

    fun setPerfumeList(list: MutableList<PerfumeInfo>) {
        _perfumeList.value = list
        Log.e("setPerfumeList", list.toString())
        Log.e("setPerfumeList", _perfumeList.value.toString())
    }

    fun addKeywordList(index: Int, boolean: Boolean) {
        if (boolean) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
            selectedKeywordList.value = tempSelectedKeywordList

            Log.e("index", index.toString())
            Log.e("add keyword", selectedKeywordList.value.toString())
        }
        else{
            selectedKeywordList.value?.remove(index)

            Log.e("index", index.toString())
            Log.e("remove keyword", selectedKeywordList.value.toString())
        }
    }

    fun addSeriesList(index: Int) {
        if (selectedSeriesList.value != null) tempSelectedSeriesList = selectedSeriesList.value!!

        if (!tempSelectedSeriesList.contains(index)) tempSelectedSeriesList.add(index)
        selectedSeriesList.value = tempSelectedSeriesList

        Log.e("index", index.toString())
        Log.e("add series", selectedSeriesList.value.toString())
    }

    fun removeSeriesList(index: Int) {
        selectedSeriesList.value?.remove(index)
        Log.e("index", index.toString())
        Log.e("remove series", selectedSeriesList.value.toString())
    }

    fun postSurvey(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = RequestSurvey(
                selectedKeywordList.value,
                selectedSeriesList.value,
                selectedPerfumeList.value
            )
            val message = surveyRepository.postSurvey(
                token,
                request
            )
            Log.e("request", request.toString())
            Log.e("survey post", message)
        }
    }


}