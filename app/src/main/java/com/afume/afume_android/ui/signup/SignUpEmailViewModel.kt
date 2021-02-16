package com.afume.afume_android.ui.signup

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpEmailViewModel : ViewModel() {
    // 입력 내용
    val emailText = MutableLiveData<String>("")
    val nickText = MutableLiveData<String>("")
    val passwordText = MutableLiveData<String>("")

    // 안내문 내용
    val emailNotice = MutableLiveData<String>()
    val nickNotice = MutableLiveData<String>()

    // 이메일 형식 검사 - 하단 안내문
    private val _isValidEmailNotice = MutableLiveData<Boolean>(false)
    val isValidEmailNotice : LiveData<Boolean>
        get() = _isValidEmailNotice

    // 이메일 형식 검사 - 우측 이미지
    private val _isValidEmailImage = MutableLiveData<Boolean>(false)
    val isValidEmailImage : LiveData<Boolean>
        get() = _isValidEmailImage

    // 이메일 중복확인
    private val _isValidEmail = MutableLiveData<Boolean>(true)
    val isValidEmail : LiveData<Boolean>
        get() = _isValidEmail

    // 닉네임 형식 검사 - 하단 안내문
    private val _isValidNickNotice = MutableLiveData<Boolean>(false)
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 닉네임 형식 검사 - 우측 이미지
    private val _isValidNickImage = MutableLiveData<Boolean>(false)
    val isValidNickImage : LiveData<Boolean>
        get() = _isValidNickImage

    // 닉네임 중복확인
    private val _isValidNick = MutableLiveData<Boolean>(true)
    val isValidNick : LiveData<Boolean>
        get() = _isValidNick

    // 닉네임 입력란 노출
    private val _nick = MutableLiveData<Boolean>(false)
    val nick : LiveData<Boolean>
        get() = _nick

    // 다음 버튼 노출
    private val _nextBtn = MutableLiveData<Boolean>(false)
    val nextBtn : LiveData<Boolean>
        get() = _nextBtn

    // 이메일 입력 실시간 확인
    fun checkEmailInput(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 1500L)
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        emailNotice.value = "이메일 형식이 맞지 않습니다."
        _isValidEmailNotice.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.value.toString()).matches()
    }

    // 이메일 중복확인
    fun doubleCheckEmail(){
        if(emailText.value == "afume@naver.com"){
            emailNotice.value = "이미 사용 중인 이메일입니다."
            _isValidEmailNotice.value = true
            _isValidEmailImage.value = false
        }else{
            _isValidEmail.value = false
            _isValidEmailNotice.value = false
            _isValidEmailImage.value = true

            _nick.value = true
        }
    }

    // 닉네임 입력 실시간 확인
    fun checkNickInput(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkNickForm()},0L)
    }

    // 닉네임 형식 확인
    private fun checkNickForm() {
        nickNotice.value = "특수문자는 사용하실 수 없습니다."
        val nickPattern: Pattern = Pattern.compile("[ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z0-9]{0,30}")

        _isValidNickNotice.value = !nickPattern.matcher(nickText.value.toString()).matches()
    }

    // 닉네임 중복확인
    fun doubleCheckNick(){
        if(nickText.value == "아"){
            nickNotice.value = "이미 등록된 닉네임입니다."
            _isValidNickNotice.value = true
            _isValidNickImage.value = false
        }else{
            _isValidNick.value = false
            _isValidNickNotice.value = false
            _isValidNickImage.value = true
        }
    }

    // 다음 버튼 노출 여부 검사
    fun checkNextBtn(){
        _nextBtn.value = _isValidEmailImage.value == true && _isValidNickImage.value == true
    }

}