package com.scentsnote.android.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scentsnote.android.BuildConfig
import com.scentsnote.android.data.repository.SplashRepository
import com.scentsnote.android.utils.etc.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SplashViewModel : ViewModel() {
    private val splashRepository = SplashRepository()

    // 현재 앱 버전 지원 여부 확인
    private val _isValidVersion = MutableLiveData<String>()
    val isValidVersion : LiveData<String>
        get() = _isValidVersion

    suspend fun getVersion(){
        withContext(viewModelScope.coroutineContext) {
            delay(1000)
            try {
                _isValidVersion.value = if(splashRepository.getVersion(BuildConfig.VERSION_NAME)) "pass" else "update"
                Log.d("getVersion", _isValidVersion.value.toString())
            } catch (e: HttpException) {
                Log.e("getVersion error", "${e.code()} / ${e.message()}")
                _isValidVersion.value = "error"
            }
        }

    }
}