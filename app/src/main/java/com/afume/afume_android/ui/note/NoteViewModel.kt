package com.afume.afume_android.ui.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.NoteRepository
import com.afume.afume_android.data.repository.SurveyRepository
import com.afume.afume_android.data.vo.response.KeywordInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NoteViewModel : ViewModel() {
    private val surveyRepository = SurveyRepository()
    private val noteRepository = NoteRepository()

    private val _keywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    val keywordList: LiveData<MutableList<KeywordInfo>> get() = _keywordList

    val selectedKeywordList: MutableLiveData<MutableList<KeywordInfo>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<KeywordInfo>()

    var selectedKeywordIdxList = mutableListOf<Int>()

    init {
        viewModelScope.launch {
            _keywordList.value = surveyRepository.getKeyword()
        }
    }

    // 입력 내용
    val contentsTxt = MutableLiveData<String>("")

    // RatingBar
    val rating = MutableLiveData<Float>(0f)

    // SeekBar
    val longevityProgress = MutableLiveData<Int>()
    val reverbProgress = MutableLiveData<Int>()
    val genderProgress = MutableLiveData<Int>()

    // 계절 선택
    var selectedSeasonList = mutableListOf<String>()

    private val _springBtn = MutableLiveData<Boolean>(false)
    val springBtn : LiveData<Boolean>
        get() = _springBtn

    private val _summerBtn = MutableLiveData<Boolean>(false)
    val summerBtn : LiveData<Boolean>
        get() = _summerBtn

    private val _fallBtn = MutableLiveData<Boolean>(false)
    val fallBtn : LiveData<Boolean>
        get() = _fallBtn

    private val _winterBtn = MutableLiveData<Boolean>(false)
    val winterBtn : LiveData<Boolean>
        get() = _winterBtn

    fun onClickSpringBtn(){
        _springBtn.value = _springBtn.value != true
    }

    fun onClickSummerBtn(){
        _summerBtn.value = _summerBtn.value != true
    }

    fun onClickFallBtn(){
        _fallBtn.value = _fallBtn.value != true
    }

    fun onClickWinterBtn(){
        _winterBtn.value = _winterBtn.value != true
    }

    // 공유 버튼 활성화
    private val _isValidShareBtn = MutableLiveData<Boolean>(false)
    val isValidShareBtn : LiveData<Boolean>
        get() = _isValidShareBtn

    // 공유 버튼 체크
    private val _shareBtn = MutableLiveData<Boolean>(false)
    val shareBtn : LiveData<Boolean>
        get() = _shareBtn

    // 완료 버튼 활성화
    private val _completeBtn = MutableLiveData<Boolean>(false)
    val completeBtn : LiveData<Boolean>
        get() = _completeBtn

    // 삭제,수정 버튼 활성화
    private val _isValidUpdateBtn = MutableLiveData<Boolean>(false)
    val isValidUpdateBtn : LiveData<Boolean>
        get() = _isValidUpdateBtn

    // 키워드 리싸이클러뷰 노출 여부
    private val _rvKeywordList = MutableLiveData<Boolean>(false)
    val rvKeywordList : LiveData<Boolean>
        get() = _rvKeywordList

    fun addKeywordList(keyword: KeywordInfo, add: Boolean) {
        if (add) {
            if (selectedKeywordList.value != null) tempSelectedKeywordList =
                selectedKeywordList.value!!

            if (!tempSelectedKeywordList.contains(keyword)) tempSelectedKeywordList.add(keyword)
            selectedKeywordList.value = tempSelectedKeywordList
        } else {
            if(tempSelectedKeywordList.contains(keyword)) tempSelectedKeywordList.remove(keyword)
            selectedKeywordList.value = tempSelectedKeywordList
        }
        checkKeywordList()
    }

    private fun checkKeywordList(){
        if(selectedKeywordList.value?.isNotEmpty() == true){
            _rvKeywordList.postValue(true)
        }else{
            _rvKeywordList.postValue(false)
        }
    }

    fun setShareBtn(){
        _shareBtn.value = _shareBtn.value != true
    }

    fun checkShareBtn(){
        if(contentsTxt.value?.isNotEmpty() == true && rating.value != 0f && selectedKeywordList.value?.isNotEmpty() == true
            && longevityProgress.value != null && reverbProgress.value != null && genderProgress.value != null
            &&(_springBtn.value == true || _summerBtn.value == true || _fallBtn.value == true || _winterBtn.value == true)){
            _isValidShareBtn.postValue(true)
        }else{
            _isValidShareBtn.postValue(false)
            _shareBtn.value = false
        }
    }

    fun checkCompleteBtn(){
        if(contentsTxt.value?.isNotEmpty() == true && rating.value != 0f){
            _completeBtn.postValue(true)
        }else{
            _completeBtn.postValue(false)
        }
    }

    // 시향노트 추가
    fun postReview(perfumeIdx : Int){
        _isValidUpdateBtn.postValue(false)
        viewModelScope.launch {
            try{
                val reviewInfo = RequestReview(
                    score = rating.value!!,
                    longevity = getLongevity(longevityProgress.value?: -1),
                    sillage = getReverb(reverbProgress.value?: -1),
                    seasonal = getSeason(),
                    gender = getGender(genderProgress.value?: -1),
                    access = _shareBtn.value!!,
                    content = contentsTxt.value!!,
                    keywordList = getKeyword()
                )

                Log.d("명 : ", reviewInfo.toString())

                noteRepository.postReview(
                    AfumeApplication.prefManager.accessToken,
                    perfumeIdx,
                    reviewInfo
                ).let {
                    Log.d("시향 노트 추가 성공 : ", it)
                }
            }catch (e: HttpException){
                when(e.response()?.code()){
                    401 -> { // 잘못된 토큰
                        Log.d("시향 노트 추가 실패 : ", e.message())
                    }
                }
            }
        }
    }

    private fun getLongevity(longevity : Int):String{
        return when (longevity) {
            0 -> "매우 약함"
            1 -> "약함"
            2 -> "보통"
            3 -> "강함"
            4 -> "매우 강함"
            else -> ""
        }
    }

    private fun getReverb(reverb : Int):String{
        return when (reverb) {
            0 -> "가벼움"
            1 -> "보통"
            2 -> "무거움"
            else -> ""
        }
    }

    private fun getGender(gender : Int):String{
        return when (gender) {
            0 -> "남성"
            1 -> "중성"
            2 -> "여성"
            else -> ""
        }
    }

    private fun getSeason() : MutableList<String>{
        if(_springBtn.value == true) selectedSeasonList.add("봄")
        if(_summerBtn.value == true) selectedSeasonList.add("여름")
        if(_fallBtn.value == true) selectedSeasonList.add("가을")
        if(_winterBtn.value == true) selectedSeasonList.add("겨울")

        return selectedSeasonList
    }

    private fun getKeyword() : MutableList<Int>{
        selectedKeywordList.value?.forEachIndexed{ _, keywordInfo ->
            selectedKeywordIdxList.add(keywordInfo.keywordIdx)
        }
        return selectedKeywordIdxList
    }

    fun deleteReview(reviewIdx: Int){
        viewModelScope.launch {
            try{
                noteRepository.deleteReview(AfumeApplication.prefManager.accessToken, reviewIdx).let {
                    Log.d("시향 노트 삭제 성공 : ", it)
                }
            }catch (e : HttpException){
                when(e.response()?.code()){
                    400 -> { // 잘못된 접근 : 자신의 리뷰 아닌 경우
                        Log.d("시향 노트 추가 실패 : ", e.message())
                    }
                    401 -> { // 잘못된 토큰
                        Log.d("시향 노트 추가 실패 : ", e.message())
                    }
                }
            }

        }
    }

    fun fetReview(reviewIdx: Int){
//        viewModelScope.launch {
//            try{
//                noteRepository.getReview(reviewIdx).let {
//                    rating.value = it.score
//                    longevityProgress.value = it.longevity
//                    reverbProgress.value = it.sillage
//                    genderProgress.value = it.gender
//                    _shareBtn.value = it.access
//                    contentsTxt.value = it.content
//                }
//            }
//        }
    }
}