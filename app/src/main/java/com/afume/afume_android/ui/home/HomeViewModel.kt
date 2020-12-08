package com.afume.afume_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.data.repository.HomeRepository
import com.afume.afume_android.data.vo.RecommendPerfumeListData
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel : ViewModel(){
    private val homeRepository = HomeRepository()
    private val disposables = CompositeDisposable()

    private var _recommendList = MutableLiveData<ArrayList<RecommendPerfumeListData>>()
    val recommendList : LiveData<ArrayList<RecommendPerfumeListData>> get() = _recommendList
}