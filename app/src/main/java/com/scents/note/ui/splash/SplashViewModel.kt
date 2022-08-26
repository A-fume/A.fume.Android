package com.scents.note.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scents.note.BuildConfig
import com.scents.note.data.repository.SplashRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SplashViewModel : ViewModel() {
    private val splashRepository = SplashRepository()

    // 버전 지원 여부 확인
    private val _isValidVersion = MutableLiveData<Boolean>(false)
    val isValidVersion : LiveData<Boolean>
        get() = _isValidVersion

    init {
        viewModelScope.launch{
            getVersion()
        }
    }

    private suspend fun getVersion(){
        try{
            _isValidVersion.value = splashRepository.getVersion(BuildConfig.VERSION_NAME)
            Log.d("getVersion", _isValidVersion.value.toString())
        }catch (e : HttpException){
            Log.d("getVersion error", e.message())
        }
    }
}