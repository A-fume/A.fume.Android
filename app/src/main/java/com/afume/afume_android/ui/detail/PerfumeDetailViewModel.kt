package com.afume.afume_android.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.PerfumeDetailRepository
import com.afume.afume_android.data.vo.request.RequestReportReview
import com.afume.afume_android.data.vo.response.PerfumeDetail
import com.afume.afume_android.data.vo.response.PerfumeDetailWithReviews
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PerfumeDetailViewModel: ViewModel() {
    private val repo = PerfumeDetailRepository()
    private val compositeDisposable = CompositeDisposable()

    private val _perfumeDetailData: MutableLiveData<PerfumeDetail> = MutableLiveData()
    val perfumeDetailData: LiveData<PerfumeDetail> get() = _perfumeDetailData

    // keyword 영역
    private val _isValidKeywordData = MutableLiveData<Boolean>(false)
    val isValidKeywordData : LiveData<Boolean>
        get() = _isValidKeywordData

    // note 영역
    private val _isValidNoteData = MutableLiveData<Boolean>(false)
    val isValidNoteData : LiveData<Boolean>
        get() = _isValidNoteData

    fun getPerfumeInfo(perfumeIdx: Int) {
        compositeDisposable.add(
            repo.getPerfumeDetail(AfumeApplication.prefManager.accessToken, perfumeIdx)
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

        _isValidNoteData.value = data.noteType == 0
    }

    private val _perfumeDetailWithReviewData: MutableLiveData<List<PerfumeDetailWithReviews>> = MutableLiveData()
    val perfumeDetailWithReviewData: LiveData<List<PerfumeDetailWithReviews>> get() = _perfumeDetailWithReviewData

    private val _isValidNoteList = MutableLiveData<Boolean>(false)
    val isValidNoteList: LiveData<Boolean> get() = _isValidNoteList

    fun getPerfumeInfoWithReview(perfumeIdx: Int) {
        compositeDisposable.add(
            repo.getPerfumeDetailWithReviews(AfumeApplication.prefManager.accessToken, perfumeIdx)
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
            repo.postPerfumeLike(AfumeApplication.prefManager.accessToken, perfumeIdx)
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
                repo.postReviewLike(AfumeApplication.prefManager.accessToken, reviewIdx).let{
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
                repo.reportReview(AfumeApplication.prefManager.accessToken, reviewIdx, RequestReportReview(reportTxt.value!!) ).let {
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