package com.afume.afume_android.ui.signup

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    // 이메일 내용
    val emailText = MutableLiveData<String>("")

    // 안내문 내용
    val emailNotice = MutableLiveData<String>()

    // 이메일 형식 검사 - 하단 안내문
    private val _checkEmailNotice = MutableLiveData<Boolean>(false)
    val checkEmailNotice : LiveData<Boolean>
        get() = _checkEmailNotice

    // 이메일 형식 검사 - 우측 이미지
    private val _checkEmailImage = MutableLiveData<Boolean>(false)
    val checkEmailImage : LiveData<Boolean>
        get() = _checkEmailImage

    // 이메일 중복확인
    private val _doubleCheckEmail = MutableLiveData<Boolean>(true)
    val doubleCheckEmail : LiveData<Boolean>
        get() = _doubleCheckEmail

    // 이메일 입력 실시간 확인
    fun checkEmailInput(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 1500L)
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        emailNotice.value = "이메일 형식이 맞지 않습니다."
        _checkEmailNotice.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.value.toString()).matches()
    }

    // 이메일 중복확인
    fun doubleCheck(){
        if(emailText.value == "afume@naver.com"){
            emailNotice.value = "이미 사용 중인 이메일 입니다."
            _checkEmailNotice.value = true
            _checkEmailImage.value = false
        }else{
            _doubleCheckEmail.value = false
            _checkEmailNotice.value = false
            _checkEmailImage.value = true
        }
    }
}