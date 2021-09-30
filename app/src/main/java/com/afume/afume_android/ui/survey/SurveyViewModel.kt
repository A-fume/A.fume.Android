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
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SurveyViewModel : ViewModel() {
    private val surveyRepository = SurveyRepository()
    var txtButton=MutableLiveData("다음")

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

        getSeries()
        getKeyword()
        getSurveyPerfume()

        viewModelScope.launch {
            Log.d("perfume value", _perfumeList.value.toString())
            Log.d("ser value", _seriesList.value.toString())
            Log.d("mutavle", tempSelectedPerfumeList.toString())
            selectedPerfumeList.value = tempSelectedPerfumeList
            selectedKeywordList.value = tempSelectedKeywordList
            selectedSeriesList.value = tempSelectedSeriesList
        }
    }

    fun setActiveButton(pos:Int){
        when(pos){
            0,1 -> txtButton.value="다음"
            2->txtButton.value="완료"
        }
    }
    fun addPerfumeList(index: Int) {
        if (selectedPerfumeList.value != null) tempSelectedPerfumeList =
            selectedPerfumeList.value!!

        if (!tempSelectedKeywordList.contains(index)) tempSelectedPerfumeList.add(index)
        selectedPerfumeList.value = tempSelectedPerfumeList


        Log.d("index", index.toString())
        Log.d("add perfume", selectedPerfumeList.value.toString())
    }

    fun removePerfumeList(index: Int) {
        selectedPerfumeList.value?.remove(index)
        Log.d("index", index.toString())
        Log.d("remove perfume", selectedPerfumeList.value.toString())
    }

    fun setPerfumeList(list: MutableList<PerfumeInfo>) {
        _perfumeList.value = list
        Log.d("setPerfumeList", list.toString())
        Log.d("setPerfumeList", _perfumeList.value.toString())
    }

    fun addKeywordList(index: Int, boolean: Boolean) {
        if (boolean) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
            selectedKeywordList.value = tempSelectedKeywordList

            Log.d("index", index.toString())
            Log.d("add keyword", selectedKeywordList.value.toString())
        } else {
            selectedKeywordList.value?.remove(index)

            Log.d("index", index.toString())
            Log.d("remove keyword", selectedKeywordList.value.toString())
        }
    }

    fun addSeriesList(index: Int) {
        if (selectedSeriesList.value != null) tempSelectedSeriesList = selectedSeriesList.value!!

        if (!tempSelectedSeriesList.contains(index)) tempSelectedSeriesList.add(index)
        selectedSeriesList.value = tempSelectedSeriesList

        Log.d("index", index.toString())
        Log.d("add series", selectedSeriesList.value.toString())
    }

    fun removeSeriesList(index: Int) {
        selectedSeriesList.value?.remove(index)
        Log.d("index", index.toString())
        Log.d("remove series", selectedSeriesList.value.toString())
    }
    fun getSeries(){
        viewModelScope.launch{
            try { _seriesList.value = surveyRepository.getSeries()}
            catch (e: HttpException){}
        }

        Log.d("ser value", _seriesList.value.toString())
    }
    fun getSurveyPerfume(){
        viewModelScope.launch{
            try { _perfumeList.value = surveyRepository.getSurveyPerfume()}
            catch (e: HttpException){}
        }
    }
    fun getKeyword(){
        viewModelScope.launch{
            try {
                val keyword = surveyRepository.getKeyword()
                keyword.forEach {
                    it.clickable = true
                }
                _keywordList.value = keyword

                Log.d("keywordList",keywordList.value.toString())
            }
            catch (e: HttpException){}
        }
    }

    fun postSurvey(token: String) {
        viewModelScope.launch {
            try {
                val request = RequestSurvey(
                    selectedKeywordList.value,
                    selectedSeriesList.value,
                    selectedPerfumeList.value
                )
                val message = surveyRepository.postSurvey(
                    token,
                    request
                )
                Log.d("request", request.toString())
                Log.d("survey post", message)
            } catch (e: HttpException) {

            }
        }
    }


}