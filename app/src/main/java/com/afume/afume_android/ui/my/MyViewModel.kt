package com.afume.afume_android.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.MyRepository
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.ResponseMyPerfume
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val myRepository = MyRepository()

    private val _wishList: MutableLiveData<MutableList<PerfumeInfo>> = MutableLiveData()
    val wishList: LiveData<MutableList<PerfumeInfo>> get() = _wishList

    private val _myPerfumeList: MutableLiveData<MutableList<ResponseMyPerfume>> = MutableLiveData()
    val myPerfumeList: LiveData<MutableList<ResponseMyPerfume>> get() = _myPerfumeList

    init {
        viewModelScope.launch {
            _wishList.value = myRepository.getLikedPerfume(
                "", 0
            )
            _myPerfumeList.value = myRepository.getMyPerfume(
                "",0
            )
        }
    }

}
