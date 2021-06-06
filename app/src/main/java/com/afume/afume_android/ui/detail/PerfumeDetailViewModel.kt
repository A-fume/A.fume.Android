package com.afume.afume_android.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.PerfumeDetailRepository
import com.afume.afume_android.data.vo.response.PerfumeDetail
import com.afume.afume_android.data.vo.response.PerfumeDetailWithReviews
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PerfumeDetailViewModel: ViewModel() {
    private val repo = PerfumeDetailRepository()
    private val compositeDisposable = CompositeDisposable()

    private val _perfumeDetailData: MutableLiveData<PerfumeDetail> = MutableLiveData()
    val perfumeDetailData: LiveData<PerfumeDetail> get() = _perfumeDetailData

    fun getPerfumeInfo(perfumeIdx: Int) {
        compositeDisposable.add(
            repo.getPerfumeDetail(perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _perfumeDetailData.postValue(it.data)
                    Log.d("getPerfumeInfo", it.toString())
                }) {
                    Log.d("getPerfumeInfo error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private val _perfumeDetailWithReviewData: MutableLiveData<List<PerfumeDetailWithReviews>> = MutableLiveData()
    val perfumeDetailWithReviewData: LiveData<List<PerfumeDetailWithReviews>> get() = _perfumeDetailWithReviewData

    fun getPerfumeInfoWithReview(perfumeIdx: Int) {
        compositeDisposable.add(
            repo.getPerfumeDetailWithReviews(perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("DETAILDATAWithReviews", it.toString())
                    _perfumeDetailWithReviewData.postValue(it.data)
                }) {
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
}