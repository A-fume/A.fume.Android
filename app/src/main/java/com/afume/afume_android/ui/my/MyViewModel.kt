package com.afume.afume_android.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.MyRepository
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.util.getToken
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val myRepository = MyRepository()

    private val _wishList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val wishList: LiveData<MutableList<PerfumeInfo>> get() = _wishList

    init {
        viewModelScope.launch{
            _wishList.value=myRepository.getLikedPerfume(
                getToken(), 1
            )
        }
    }
}
