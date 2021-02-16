package com.afume.afume_android.ui.signup

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel  : ViewModel() {
    // 입력 내용
    val passwordText = MutableLiveData<String>("")
    val againPasswordText = MutableLiveData<String>("")

    // 비밀번호 형식 검사 - 하단 안내문
    private val _isValidPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 비밀번호 형식 검사 - 우측 이미지
    private val _isValidPasswordImage = MutableLiveData<Boolean>(false)
    val isValidPasswordImage : LiveData<Boolean>
        get() = _isValidPasswordImage

    // 비밀번호 일치 검사 - 하단 안내문
    private val _isValidAgainNotice = MutableLiveData<Boolean>(false)
    val isValidAgainNotice : LiveData<Boolean>
        get() = _isValidAgainNotice

    // 비밀번호 일치 검사 - 우측 이미지
    private val _isValidAgainImage = MutableLiveData<Boolean>(false)
    val isValidAgainImage : LiveData<Boolean>
        get() = _isValidAgainImage

    // 비밀번호 확인란 노출
    private val _againPassword = MutableLiveData<Boolean>(false)
    val againPassword : LiveData<Boolean>
        get() = _againPassword

    // 다음 버튼 노출
    private val _nextBtn = MutableLiveData<Boolean>(false)
    val nextBtn : LiveData<Boolean>
        get() = _nextBtn

    // 비밀번호 입력 실시간 확인
    fun checkPasswordInput(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 0L)
    }

    // 비밀번호 자리수 확인
    private fun checkEmailForm() {
        if(passwordText.value.toString().length<4){
            _isValidPasswordNotice.value = true
            _isValidPasswordImage.value = false
        }else{
            _isValidPasswordNotice.value = false
            _isValidPasswordImage.value = true

            _againPassword.value = true
        }
    }

    // 비밀번호 확인 실시간 확인
    fun checkAgainInput(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkAgainForm() }, 0L)
    }

    // 비밀번호 일치 확인
    private fun checkAgainForm(){
        if(againPasswordText.value.toString() == passwordText.value.toString()){
            _isValidAgainNotice.value = false
            _isValidAgainImage.value = true

            _nextBtn.value = true
        }else{
            _isValidAgainNotice.value = true
            _isValidAgainImage.value = false
        }
    }

    // 다음 버튼 노출 여부 검사
    fun checkNextBtn(){
        _nextBtn.value = _isValidPasswordImage.value == true && _isValidAgainImage.value == true
    }

}