package com.scentsnote.android.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.repository.HomeRepository
import com.scentsnote.android.data.repository.PerfumeDetailRepository
import com.scentsnote.android.data.vo.request.RequestReportReview
import com.scentsnote.android.data.vo.response.PerfumeDetail
import com.scentsnote.android.data.vo.response.PerfumeDetailWithReviews
import com.scentsnote.android.data.vo.response.RecommendPerfumeItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PerfumeDetailViewModel: ViewModel() {
    private val homeRepo = HomeRepository()
    private val detailRepo = PerfumeDetailRepository()
    private val compositeDisposable = CompositeDisposable()

    private val _perfumeDetailData: MutableLiveData<PerfumeDetail> = MutableLiveData()
    val perfumeDetailData: LiveData<PerfumeDetail> get() = _perfumeDetailData

    private val _similarPerfumeList : MutableLiveData<MutableList<RecommendPerfumeItem>> = MutableLiveData()
    val similarPerfumeList : LiveData<MutableList<RecommendPerfumeItem>>
        get() = _similarPerfumeList

    // keyword 영역
    private val _isValidKeywordData = MutableLiveData<Boolean>(false)
    val isValidKeywordData : LiveData<Boolean>
        get() = _isValidKeywordData

    // note 영역
    private val _isValidNoteData = MutableLiveData<Boolean>(true)
    val isValidNoteData : LiveData<Boolean>
        get() = _isValidNoteData

    private val _noteDataType = MutableLiveData<Boolean>(false)
    val noteDataType : LiveData<Boolean>
        get() = _noteDataType

    // price 영역
    private val _isValidPriceData = MutableLiveData<Boolean>(false)
    val isValidPriceData : LiveData<Boolean>
        get() = _isValidPriceData

    // season 영역
    private val _isValidSeasonChart = MutableLiveData<Boolean>(true)
    val isValidSeasonChart : LiveData<Boolean>
        get() = _isValidSeasonChart

    // gender 영역
    private val _isValidGenderChart = MutableLiveData<Boolean>(true)
    val isValidGenderChart : LiveData<Boolean>
        get() = _isValidGenderChart

    fun getPerfumeInfo(perfumeIdx: Int) {
        compositeDisposable.add(
            detailRepo.getPerfumeDetail(ScentsNoteApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _perfumeDetailData.postValue(it.data)

                    setDataVisible(it.data)

                    Log.d("getPerfumeInfo", it.toString())
                }) {
                    Log.d("getPerfumeInfo error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private fun setDataVisible(data: PerfumeDetail){
        _isValidKeywordData.value = data.Keywords.isNotEmpty()

        _isValidPriceData.value = data.volumeAndPrice.isNotEmpty()

        _noteDataType.value = data.noteType == 1

        if(data.noteType == 0 && data.ingredients.top.isEmpty() && data.ingredients.middle.isEmpty() && data.ingredients.base.isEmpty()) _isValidNoteData.value = false

        if(data.seasonal.spring == 0 && data.seasonal.summer == 0 && data.seasonal.fall == 0 && data.seasonal.winter == 0) _isValidSeasonChart.value = false

        if(data.gender.female == 0 && data.gender.female == 0 && data.gender.female == 0) _isValidGenderChart.value = false
    }

    @SuppressLint("LongLogTag")
    fun getSimilarPerfumeList(perfumeIdx: Int){
        viewModelScope.launch {
            try{
                _similarPerfumeList.value = detailRepo.getSimilarPerfumeList(perfumeIdx)
                Log.d("getSimilarPerfumeList", _similarPerfumeList.value.toString())
            }catch (e : HttpException){
                Log.d("getSimilarPerfumeList error", _similarPerfumeList.value.toString())
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun postSimilarPerfumeLike(type: Int, perfumeIdx: Int) {
        compositeDisposable.add(
            homeRepo.postPerfumeLike(ScentsNoteApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    clickHeartPerfumeList(_similarPerfumeList, perfumeIdx,it.data)
                }) {
                    Log.d("postSimilarPerfumeLike error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private fun clickHeartPerfumeList(perfumeList: MutableLiveData<MutableList<RecommendPerfumeItem>>, perfumeIdx: Int, isSelected:Boolean){
        val tempList = perfumeList.value
        tempList?.forEach { if(it.perfumeIdx==perfumeIdx) it.isLiked= isSelected}
        perfumeList.value=tempList
    }

    private val _perfumeDetailWithReviewData: MutableLiveData<List<PerfumeDetailWithReviews>> = MutableLiveData()
    val perfumeDetailWithReviewData: LiveData<List<PerfumeDetailWithReviews>> get() = _perfumeDetailWithReviewData

    private val _isValidNoteList = MutableLiveData<Boolean>(false)
    val isValidNoteList: LiveData<Boolean> get() = _isValidNoteList

    @SuppressLint("LongLogTag")
    fun getPerfumeInfoWithReview(perfumeIdx: Int) {
        compositeDisposable.add(
            detailRepo.getPerfumeDetailWithReviews(ScentsNoteApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _perfumeDetailWithReviewData.postValue(it.data)

                    _isValidNoteList.value = it.data.isNotEmpty()

                    Log.d("DETAILDATAWithReviews", it.toString())
                }) {
                    _isValidNoteList.postValue(false)
                    Log.d("DETAILDATAWithReviews error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private val _perfumeLike: MutableLiveData<Boolean> = MutableLiveData()
    val perfumeLike: LiveData<Boolean> get() = _perfumeLike

    fun postPerfumeLike(perfumeIdx: Int) {
        compositeDisposable.add(
            detailRepo.postPerfumeLike(ScentsNoteApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("postPerfumeLike", it.toString())
                    _perfumeLike.postValue(it.data)
                }) {
                    Log.d("postPerfumeLike error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private val _reviewLike: MutableLiveData<Boolean> = MutableLiveData()

    fun postReviewLike(reviewIdx: Int){
        viewModelScope.launch {
            try{
                detailRepo.postReviewLike(ScentsNoteApplication.prefManager.accessToken, reviewIdx).let{
                    _reviewLike.postValue(it)
                    clickHeartNoteList(reviewIdx, it)
                    Log.d("시향 노트 좋아요 상태 : ", it.toString())
                }
            }catch (e : HttpException){
                when(e.response()?.code()){
                    401 -> { // 인증되지 않은 사용자
                        Log.d("시향 노트 좋아요 실패 : ", e.message())
                    }
                    403 -> { // 잘못된 접근
                        Log.d("시향 노트 좋아요 실패 : ", e.message())
                    }
                }
            }
        }
    }

    private fun clickHeartNoteList(reviewIdx: Int, isSelected:Boolean){
        val tempList = _perfumeDetailWithReviewData.value
        tempList?.forEach {
            if(it.reviewIdx==reviewIdx) {
                it.isLiked= isSelected
                if(it.isLiked){
                    it.likeCount += 1
                }else{
                    it.likeCount -= 1
                }
            }
        }
        _perfumeDetailWithReviewData.value=tempList
    }

    var reportTxt = MutableLiveData<String>("")

    fun setReportTxt(txt:String){
        reportTxt.value = txt
    }

    private val _isValidReport: MutableLiveData<Boolean> = MutableLiveData()
    val isValidReport: LiveData<Boolean> get() = _isValidReport

    fun reportReview(reviewIdx: Int){
        viewModelScope.launch {
            try{
                detailRepo.reportReview(ScentsNoteApplication.prefManager.accessToken, reviewIdx, RequestReportReview(reportTxt.value!!) ).let {
                    _isValidReport.postValue(true)
                    Log.d("시향 노트 신고 성공",it)
                }
            }catch (e : HttpException){
                _isValidReport.postValue(false)
                Log.d("시향 노트 신고 실패 : ", e.message())
            }
        }
    }
}