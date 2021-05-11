package com.afume.afume_android.ui.note

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

    // 완료 버튼 활성화
    private val _completeBtn = MutableLiveData<Boolean>(false)
    val completeBtn : LiveData<Boolean>
        get() = _completeBtn

    fun addKeywordList(index: Int) {
        if (selectedKeywordList.value != null) tempSelectedKeywordList = selectedKeywordList.value!!

        if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
        selectedKeywordList.value = tempSelectedKeywordList
    }

    fun removeKeywordList(index: Int) {
        selectedKeywordList.value?.remove(index)
    }

}