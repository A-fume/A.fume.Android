package com.afume.afume_android.ui.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SurveyRepository
import com.afume.afume_android.data.vo.response.KeywordInfo
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {
    private val surveyRepository = SurveyRepository()



    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    val selectedKeywordList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<Int>()

    init {
        viewModelScope.launch {
            _keywordList.value = surveyRepository.getKeyword()
        }
    }

    // 입력 내용
    val contentsTxt = MutableLiveData<String>("")

    // seekBar
    val seekBarProgress = MutableLiveData<Int>()

    // 계절 선택
    val selectedSeasonList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedSeasonList = mutableListOf<Int>()

    fun onClickSeasonBtn(){

    }

    // 공유 버튼 활성화
    private val _shareBtn = MutableLiveData<Boolean>(false)
    val shareBtn : LiveData<Boolean>
        get() = _shareBtn

    // 완료 버튼 활성화
    private val _completeBtn = MutableLiveData<Boolean>(false)
    val completeBtn : LiveData<Boolean>
        get() = _completeBtn

    // 키워드 리싸이클러뷰 노출 여부
    private val _rvKeywordList = MutableLiveData<Boolean>(false)
    val rvKeywordList : LiveData<Boolean>
        get() = _rvKeywordList

    fun addKeywordList(index: Int) {
        if (selectedKeywordList.value != null) tempSelectedKeywordList = selectedKeywordList.value!!

        if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
        selectedKeywordList.value = tempSelectedKeywordList

        checkKeywordList()

        Log.d("명",tempSelectedKeywordList.toString())
    }

    fun removeKeywordList(index: Int) {
        selectedKeywordList.value?.remove(index)

        Log.d("명",tempSelectedKeywordList.toString())
    }

    private fun checkKeywordList(){
        if(tempSelectedKeywordList.size > 0){
            _rvKeywordList.postValue(true)
        }else{
            _rvKeywordList.postValue(false)
        }
    }

    fun checkShareBtn(){
        _shareBtn.value = _shareBtn.value != true
    }

}