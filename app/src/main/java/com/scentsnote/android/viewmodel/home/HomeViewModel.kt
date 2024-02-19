package com.scentsnote.android.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.repository.HomeRepository
import com.scentsnote.android.data.vo.response.HomePerfumeItem
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.scentsnote.android.data.vo.response.RecommendPerfumeItem
import com.scentsnote.android.utils.etc.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel(){
    private val homeRepository = HomeRepository()
    private val compositeDisposable = CompositeDisposable()

    private val _recommendPerfumeList : MutableLiveData<MutableList<RecommendPerfumeItem>> = MutableLiveData()
    val recommendPerfumeList : LiveData<MutableList<RecommendPerfumeItem>>
        get() = _recommendPerfumeList

    private val _commonPerfumeList : MutableLiveData<MutableList<HomePerfumeItem>> = MutableLiveData()
    val commonPerfumeList : LiveData<MutableList<HomePerfumeItem>>
        get() = _commonPerfumeList

    private val _recentPerfumeList : MutableLiveData<MutableList<HomePerfumeItem>> = MutableLiveData()
    val recentPerfumeList : LiveData<MutableList<HomePerfumeItem>>
        get() = _recentPerfumeList

    private val _newPerfumeList : MutableLiveData<MutableList<HomePerfumeItem>> = MutableLiveData()
    val newPerfumeList : LiveData<MutableList<HomePerfumeItem>>
        get() = _newPerfumeList

    suspend fun getHomePerfumeList(){
        getRecommendPerfumeList()
        getCommonPerfumeList()
        getRecentPerfumeList()
    }

    private suspend fun getRecommendPerfumeList(){
        try{
            _recommendPerfumeList.value = homeRepository.getRecommendPerfumeList(ScentsNoteApplication.prefManager.accessToken)
            Log.d("getRecommendPerfumeList", _recommendPerfumeList.value.toString())
        }catch (e : HttpException){
            Log.d("getRecommendPerfumeList error", e.message())
        }
    }

    private suspend fun getCommonPerfumeList(){
        try{
            _commonPerfumeList.value = homeRepository.getCommonPerfumeList(ScentsNoteApplication.prefManager.accessToken)
            Log.d("getCommonPerfumeList", _commonPerfumeList.value.toString())
        }catch (e : HttpException){
            Log.d("getCommonPerfumeList error", e.message())
        }
    }

    private suspend fun getRecentPerfumeList(){
        try{
            _recentPerfumeList.value = homeRepository.getRecentPerfumeList(ScentsNoteApplication.prefManager.accessToken)
            Log.d("getRecentPerfumeList", _recentPerfumeList.value.toString())
        }catch (e : HttpException){
            _recentPerfumeList.value = mutableListOf()
            Log.d("getRecentPerfumeList error", e.message())
        }
    }

    suspend fun getNewPerfumeList(requestSize: Int?){
        try{
            _newPerfumeList.value = homeRepository.getNewPerfumeList(requestSize)
            Log.d("getNewPerfumeList", _newPerfumeList.value.toString())
        }catch (e : HttpException){
            Log.d("getNewPerfumeList error", e.message())
        }
    }

    val perfumeList = MutableLiveData(mutableListOf<PerfumeInfo>())
    val perfumeLike: MutableLiveData<Boolean?> = MutableLiveData()

    fun postPerfumeLike(type: Int, perfumeIdx: Int) {
        compositeDisposable.add(
            homeRepository.postPerfumeLike(ScentsNoteApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    perfumeLike.postValue(it.data)
                    when(type){
                        0 -> {
                            clickHeartPerfumeList(_commonPerfumeList, perfumeIdx,it.data)
                        }
                        1 -> {
                            clickHeartPerfumeList(_recentPerfumeList, perfumeIdx,it.data)
                        }
                        2 -> {
                            clickHeartPerfumeList(_newPerfumeList, perfumeIdx,it.data)
                        }
                    }

                }) {
                    Log.d("postPerfumeLike error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private fun clickHeartPerfumeList(perfumeList: MutableLiveData<MutableList<HomePerfumeItem>>, perfumeIdx: Int, isSelected:Boolean){
        val tempList = perfumeList.value
        tempList?.forEach { if(it.perfumeIdx==perfumeIdx) it.isLiked= isSelected}
        perfumeList.value=tempList
    }
}