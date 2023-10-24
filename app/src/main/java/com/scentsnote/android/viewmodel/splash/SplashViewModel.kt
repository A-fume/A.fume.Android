package com.scentsnote.android.viewmodel.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.BuildConfig
import com.scentsnote.android.data.repository.SplashRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SplashViewModel : ViewModel() {
    private val splashRepository = SplashRepository()

    // 버전 지원 여부 확인
    private val _isValidVersion = MutableLiveData<String>()
    val isValidVersion : LiveData<String>
        get() = _isValidVersion

    init {
        viewModelScope.launch{
            getVersion()
        }
    }

    private suspend fun getVersion(){
        withContext(viewModelScope.coroutineContext) {
            delay(1000)
            try {
                _isValidVersion.value = if(splashRepository.getVersion(BuildConfig.VERSION_NAME)) "pass" else "update"
                Log.d("getVersion", _isValidVersion.value.toString())
            } catch (e: HttpException) {
                Log.d("getVersion error", e.message())
                _isValidVersion.value = "error"
            }
        }

    }
}