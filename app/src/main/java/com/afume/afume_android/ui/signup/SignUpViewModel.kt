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
    private val _isValidPasswordImg = MutableLiveData<Boolean>(false)
    val isValidPasswordImg : LiveData<Boolean>
        get() = _isValidPasswordImg

    // 비밀번호 일치 검사 - 하단 안내문
    private val _isValidAgainNotice = MutableLiveData<Boolean>(false)
    val isValidAgainNotice : LiveData<Boolean>
        get() = _isValidAgainNotice

    // 비밀번호 일치 검사 - 우측 이미지
    private val _isValidAgainImg = MutableLiveData<Boolean>(false)
    val isValidAgainImg : LiveData<Boolean>
        get() = _isValidAgainImg

    // 비밀번호 확인란 노출
    private val _againPasswordForm = MutableLiveData<Boolean>(false)
    val againPasswordForm : LiveData<Boolean>
        get() = _againPasswordForm

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
            _isValidPasswordImg.value = false
        }else{
            _isValidPasswordNotice.value = false
            _isValidPasswordImg.value = true

            if(!_againPasswordForm.value!!) _againPasswordForm.value = true
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
            _isValidAgainImg.value = true

            _nextBtn.value = true
        }else{
            _isValidAgainNotice.value = true
            _isValidAgainImg.value = false
        }
    }

    // 다음 버튼 노출 여부 검사
    fun checkNextBtn(){
        _nextBtn.value = _isValidPasswordImg.value == true && _isValidAgainImg.value == true
    }

}