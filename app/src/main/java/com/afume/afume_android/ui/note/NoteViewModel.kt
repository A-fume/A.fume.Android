package com.afume.afume_android.ui.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.NoteRepository
import com.afume.afume_android.data.repository.SurveyRepository
import com.afume.afume_android.data.vo.request.RequestReview
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.ResponseReview
import com.afume.afume_android.util.SingleLiveEvent
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
    val longevityProgress = MutableLiveData<Int>(-1)
    val reverbProgress = MutableLiveData<Int>(-1)
    val genderProgress = MutableLiveData<Int>(-1)

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

    private val _showErrorToast = SingleLiveEvent<Void>()
    val showErrorToast: LiveData<Void> = _showErrorToast

    // 완료 버튼 활성화
    private val _completeBtn = MutableLiveData<Boolean>(false)
    val completeBtn : LiveData<Boolean>
        get() = _completeBtn

    // 삭제,수정 버튼 활성화
    private val _isValidUpdateBtn = MutableLiveData<Boolean>(false)
    val isValidUpdateBtn : LiveData<Boolean>
        get() = _isValidUpdateBtn

    private val _showUpdateDialog = MutableLiveData<Boolean>(false)
    val showUpdateDialog : LiveData<Boolean>
        get() = _showUpdateDialog

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
        if(_isValidShareBtn.value == true){
            _shareBtn.value = _shareBtn.value != true
        }else{
            _showErrorToast.call()
        }
    }

    fun checkShareBtn(){
        if(contentsTxt.value?.isNotEmpty() == true && rating.value != 0f && selectedKeywordList.value?.isNotEmpty() == true
            && longevityProgress.value != -1 && reverbProgress.value != -1 && genderProgress.value != -1
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

    private val _isValidNoteAdd = MutableLiveData<String>("")
    val isValidNoteAdd : LiveData<String>
        get() = _isValidNoteAdd

    // 시향노트 추가
    fun postReview(perfumeIdx : Int){
        viewModelScope.launch {
            try{
                val reviewInfo = RequestReview(
                    score = rating.value!!,
                    longevity = longevityProgress.value,
                    sillage = reverbProgress.value,
                    seasonal = getSeason(),
                    gender = genderProgress.value,
                    access = _shareBtn.value!!,
                    content = contentsTxt.value!!,
                    keywordList = getKeyword()
                )

                noteRepository.postReview(
                    AfumeApplication.prefManager.accessToken,
                    perfumeIdx,
                    reviewInfo
                ).let {
                    _isValidNoteAdd.postValue("시향 노트가 추가되었습니다.")
                    Log.d("시향 노트 추가 성공 : ", it.reviewIdx.toString())
                }
            }catch (e: HttpException){
                _isValidNoteAdd.postValue("시향 노트 추가 실패")
                when(e.response()?.code()){
                    401 -> { // 잘못된 토큰
                        Log.d("시향 노트 추가 실패 : ", e.message())
                    }
                    else -> {
                        Log.d("시향 노트 추가 실패 : ", e.message())
                    }
                }
            }
        }
    }

    private fun getSeason() : MutableList<String>{
        selectedSeasonList.clear()

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

    // 수정사항 확인용
    private lateinit var responseReview: ResponseReview

    // 시향노트 조회
    fun getReview(reviewIdx: Int){
        _isValidUpdateBtn.postValue(true)

        viewModelScope.launch {
            try {
                noteRepository.getReview(reviewIdx).let {
                    responseReview = it
                    rating.value = it.score
                    longevityProgress.value = it.longevity
                    reverbProgress.value = it.sillage
                    genderProgress.value = it.gender
                    convertSeason(it.seasonal)
                    _shareBtn.value = it.access
                    contentsTxt.value = it.content
                    selectedKeywordList.value = it.keyword
                    checkKeywordList()
                    checkShareBtn()

                    Log.d("시향 노트 조회 성공 :", "")

                }
            } catch (e: HttpException) {
                Log.d("시향 노트 조회 실패 :", e.message())
            }
        }
    }

    private fun convertSeason(seasons : List<String>){
        seasons.forEach {
            when(it){
                "봄" -> _springBtn.value = true
                "여름" -> _summerBtn.value = true
                "가을" -> _fallBtn.value = true
                "겨울" -> _winterBtn.value = true
            }
        }
    }

    fun convertKeyword(){
        selectedKeywordList.value?.forEach { selectedKeywordInfo ->
            _keywordList.value?.forEach { keywordInfo ->
                if(selectedKeywordInfo.keywordIdx == keywordInfo.keywordIdx){
                    keywordInfo.checked = true
                }
            }
            addKeywordList(selectedKeywordInfo, true)
        }
    }

    fun checkUpdateInfo(){
        _showUpdateDialog.value = (responseReview.score != rating.value || responseReview.longevity != longevityProgress.value || responseReview.sillage != reverbProgress.value
                || responseReview.seasonal != getSeason() || responseReview.gender != genderProgress.value || responseReview.content != contentsTxt.value
                || responseReview.keyword != selectedKeywordList.value || responseReview.access != _shareBtn.value)
    }

    private val _isValidNoteUpdate = MutableLiveData<String>("")
    val isValidNoteUpdate : LiveData<String>
        get() = _isValidNoteUpdate

    // 시향노트 수정
    fun updateReview(reviewIdx: Int){
        viewModelScope.launch {
            try{
                val reviewInfo = RequestReview(
                    score = rating.value!!,
                    longevity = longevityProgress.value,
                    sillage = reverbProgress.value,
                    seasonal = getSeason(),
                    gender = genderProgress.value,
                    access = _shareBtn.value!!,
                    content = contentsTxt.value!!,
                    keywordList = getKeyword()
                )

                noteRepository.putReview(
                    AfumeApplication.prefManager.accessToken,
                    reviewIdx,
                    reviewInfo
                ).let {
                    _isValidNoteUpdate.postValue("시향 노트가 수정되었습니다.")
                    Log.d("시향 노트 수정 성공 : ", it)
                }
            }catch (e: HttpException){
                _isValidNoteUpdate.postValue("시향 노트 수정 실패")
                when(e.response()?.code()){
                    401 -> { // 잘못된 토큰
                        Log.d("시향 노트 수정 실패 : ", e.message())
                    }
                }
            }
        }
    }

    private val _isValidNoteDelete = MutableLiveData<String>("")
    val isValidNoteDelete : LiveData<String>
        get() = _isValidNoteDelete

    // 시향노트 삭제
    fun deleteReview(reviewIdx: Int){
        viewModelScope.launch {
            try{
                noteRepository.deleteReview(AfumeApplication.prefManager.accessToken, reviewIdx).let {
                    _isValidNoteDelete.postValue("시향 노트가 삭제되었습니다.")
                    Log.d("시향 노트 삭제 성공 : ", it)
                }
            }catch (e : HttpException){
                _isValidNoteDelete.postValue("시향 노트 삭제 실패")
                when(e.response()?.code()){
                    400 -> { // 잘못된 접근 : 자신의 리뷰 아닌 경우
                        Log.d("시향 노트 삭제 실패 : ", e.message())
                    }
                    401 -> { // 잘못된 토큰
                        Log.d("시향 노트 삭제 실패 : ", e.message())
                    }
                }
            }
        }
    }
}