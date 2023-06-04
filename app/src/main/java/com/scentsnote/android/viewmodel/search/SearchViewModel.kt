package com.scentsnote.android.viewmodel.search

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.repository.SearchRepository
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.RequestSearch
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.scentsnote.android.ui.search.SearchFragmentType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {
    val isValidResultData: LiveData<Boolean>
        get() = _isValidResultData
    val fragmentType: LiveData<SearchFragmentType>
        get() = _fragmentType

    private val searchRepository = SearchRepository()
    private val compositeDisposable = CompositeDisposable()

    var filter = MutableLiveData<SendFilter>()
    val perfumeList = MutableLiveData(mutableListOf<PerfumeInfo>())
    private val perfumeLike: MutableLiveData<Boolean> = MutableLiveData()
    private val _fragmentType: MutableLiveData<SearchFragmentType> =
        MutableLiveData(SearchFragmentType.HOME)

    private val _isValidResultData = MutableLiveData<Boolean>(false)

    val backButtonVisibility: LiveData<Boolean> = Transformations.map(_fragmentType) {
        it != SearchFragmentType.HOME
    }
    val filterKeywordListVisibility: LiveData<Boolean> = Transformations.map(_fragmentType) {
        it != SearchFragmentType.HOME
    }

    init {
        viewModelScope.launch {
            postSearchResultPerfume()
        }
    }

    fun setPageType(type: SearchFragmentType) {
        _fragmentType.value = type
    }

    suspend fun postSearchResultPerfume() {
        try {
            val requestSearch =
                RequestSearch("", mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>())
            val tempFilterList = filter.value?.filterInfoPList
            tempFilterList?.forEach {
                when (it.type) {
                    1 -> {
                        if (it.idx > -1) requestSearch.ingredientList?.add(it.idx)
                        else {
                            filter.value?.filterSeriesPMap?.get(it.name)?.forEach {
                                requestSearch.ingredientList?.add(it.ingredientIdx)
                            }
                        }
                    }

                    2 -> requestSearch.brandList?.add(it.idx)
                    3 -> requestSearch.keywordList?.add(it.idx)
                    4 -> requestSearch.searchText = it.name
                }
            }
            filter.value?.filterInfoPList = tempFilterList
            Log.e("Request Search ", requestSearch.toString())

            perfumeList.value = searchRepository.postResultPerfume(
                ScentsNoteApplication.prefManager.accessToken,
                requestSearch
            )
            setDataVisible()
            Log.e("search result", perfumeList.value.toString())

        } catch (e: HttpException) {
            Log.e("search fail", e.message())
        }
    }

    private fun setDataVisible() {
        _isValidResultData.value = perfumeList.value?.isNotEmpty() == true
    }

    fun cancelBtnFilter(f: FilterInfoP?) {
        var tmpFilter = filter.value
        if (f != null) {
            if (f.idx <= -1) tmpFilter?.filterSeriesPMap?.remove(f.name)
            else {

                if (f.type == 1) tmpFilter?.filterSeriesPMap?.values?.forEach { list ->
                    var index = -1
                    list.forEachIndexed { i, v ->
                        if (v.ingredientIdx == f.idx) index = i
                    }
                    if (index != -1) list.removeAt(index)
                }

            }
            tmpFilter?.filterInfoPList?.remove(f)
            filter.value = tmpFilter
        }
    }

    fun postPerfumeLike(perfumeIdx: Int, context: Context?) {
        compositeDisposable.add(
            searchRepository.postPerfumeLike(
                ScentsNoteApplication.prefManager.accessToken,
                perfumeIdx
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("postPerfumeLike", it.toString())
                    Log.e("postPerfumeLike", it.toString())
                    perfumeLike.postValue(it.data)
                    clickHeartPerfumeList(perfumeIdx, it.data)
                }) {
                    Log.d("postPerfumeLike error", it.toString())
                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    fun sendFilter(sendFilter: SendFilter) {
        setPageType(SearchFragmentType.RESULT)
        filter.value = sendFilter
    }

    private fun clickHeartPerfumeList(perfumeIdx: Int, isSelected: Boolean) {
        val tempList = perfumeList.value
        tempList?.forEach { if (it.perfumeIdx == perfumeIdx) it.isLiked = isSelected }
        perfumeList.value = tempList
    }

    fun resetHeartPerfumeList() {
        val tempList = perfumeList.value
        tempList?.forEach { it.isLiked = false }
        perfumeList.value = tempList
    }

    companion object {
        private var instance: SearchViewModel? = null
        fun getInstance() = instance ?: synchronized(SearchViewModel::class.java) {
            instance ?: SearchViewModel().also { instance = it }
        }
    }
}