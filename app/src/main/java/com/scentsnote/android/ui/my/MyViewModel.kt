package com.scentsnote.android.ui.my

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.repository.MyRepository
import com.scentsnote.android.data.vo.response.ResponseMyPerfume
import com.scentsnote.android.data.vo.response.WishPerfume
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MyViewModel : ViewModel() {
    private val myRepository = MyRepository()

    private val _wishList: MutableLiveData<MutableList<WishPerfume>> = MutableLiveData()
    val wishList: LiveData<MutableList<WishPerfume>> get() = _wishList

    private val _isValidWishList = MutableLiveData<Boolean>(false)
    val isValidWishList : LiveData<Boolean>
        get() = _isValidWishList

    private val _myPerfumeList: MutableLiveData<MutableList<ResponseMyPerfume>> = MutableLiveData()
    val myPerfumeList: LiveData<MutableList<ResponseMyPerfume>> get() = _myPerfumeList

    private val _isValidMyList = MutableLiveData<Boolean>(false)
    val isValidMyList : LiveData<Boolean>
        get() = _isValidMyList

    init {
        viewModelScope.launch {
            getMyPerfume()
            getLikedPerfume()
        }
    }

    suspend fun getLikedPerfume() {
        try {
            _wishList.value = myRepository.getLikedPerfume(
                ScentsNoteApplication.prefManager.accessToken, ScentsNoteApplication.prefManager.userIdx
            )
            _isValidWishList.value = _wishList.value?.isNotEmpty()

            Log.d("getLikedPerfume", _wishList.value.toString())
        } catch (e: HttpException) {
//                when(e.response()?.code()){}
        }
    }

    suspend  fun getMyPerfume() {
        try {
            _myPerfumeList.value = myRepository.getMyPerfume(
                ScentsNoteApplication.prefManager.accessToken
            )
            _isValidMyList.value = _myPerfumeList.value?.isNotEmpty()

            Log.d("getMyPerfume", _myPerfumeList.value.toString())
        } catch (e: HttpException) {
//                when(e.response()?.code()){}
        }
    }
}
