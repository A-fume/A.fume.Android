package com.afume.afume_android.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.HomeRepository
import com.afume.afume_android.data.vo.response.HomePerfumeItem
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
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

    private val _isValidRecentList = MutableLiveData<Boolean>(true)
    val isValidRecentList : LiveData<Boolean>
        get() = _isValidRecentList

    private val _newPerfumeList : MutableLiveData<MutableList<HomePerfumeItem>> = MutableLiveData()
    val newPerfumeList : LiveData<MutableList<HomePerfumeItem>>
        get() = _newPerfumeList

    init {
        viewModelScope.launch {
            try{
                _isValidRecentList.postValue(true)
                _recommendPerfumeList.value = homeRepository.getRecommendPerfumeList(AfumeApplication.prefManager.accessToken)
                _commonPerfumeList.value = homeRepository.getCommonPerfumeList(AfumeApplication.prefManager.accessToken)
                _newPerfumeList.value = homeRepository.getNewPerfumeList()
                _recentPerfumeList.value = homeRepository.getRecentPerfumeList(AfumeApplication.prefManager.accessToken)

                if(_recentPerfumeList.value!!.isEmpty()){
                    _isValidRecentList.postValue(false)
                }
            }catch (e : HttpException){
                when(e.response()?.code()){
                    401 -> { // 최근 검색 향수 없음
                        _isValidRecentList.postValue(false)
                    }
                }
            }

        }
    }

    val perfumeList = MutableLiveData(mutableListOf<PerfumeInfo>())
    val perfumeLike: MutableLiveData<Boolean> = MutableLiveData()

    fun postPerfumeLike(type: Int, perfumeIdx: Int) {
        compositeDisposable.add(
            homeRepository.postPerfumeLike(AfumeApplication.prefManager.accessToken, perfumeIdx)
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