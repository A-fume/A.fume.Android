package com.afume.afume_android.ui.signin

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.SignRepository
import com.afume.afume_android.data.vo.request.RequestLogin
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInViewModel : ViewModel() {
    private val signRepository = SignRepository()

    // 입력 내용
    val emailTxt = MutableLiveData<String>("")
    val passwordTxt = MutableLiveData<String>("")

    // 자동입력
    fun checkRegisterInfo(){
        if(AfumeApplication.prefManager.haveToken()){
            emailTxt.postValue(AfumeApplication.prefManager.userEmail)
            passwordTxt.postValue(AfumeApplication.prefManager.userPassword)
        }
    }

    // 이메일 입력 확인
    private val _isValidEmail = MutableLiveData<Boolean>(false)
    val isValidEmail : LiveData<Boolean>
        get() = _isValidEmail

    // 이메일 입력 실시간 확인
    fun inputEmail(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 0L)
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        _isValidEmail.postValue(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.value.toString()).matches())
    }

    // 비밀번호 입력 확인
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 비밀번호 입력 실시간 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkPasswordForm() }, 0L)
    }

    // 비밀번호 자리수 확인
    private fun checkPasswordForm() {
        when(passwordTxt.value?.length){
            in 0..3 -> {
                _isValidPassword.postValue(false)
            }
            else -> {
                _isValidPassword.postValue(true)
            }
        }
    }

    // 로그인 버튼 활성화
    private val _loginNextBtn = MutableLiveData<Boolean>(false)
    val loginNextBtn : LiveData<Boolean>
        get() = _loginNextBtn

    // 로그인 버튼 활성화 여부
    fun checkLoginNextBtn(){
        if(_isValidEmail.value == true && _isValidPassword.value == true){
            _loginNextBtn.postValue(true)
        }else{
            _loginNextBtn.postValue(false)
        }
    }

    // 로그인 성공 여부
    private val _isValidLogin = MutableLiveData<Boolean>(false)
    val isValidLogin : LiveData<Boolean>
        get() = _isValidLogin

    // 하단 안내문
    private val _isValidLoginNotice = MutableLiveData<Boolean>(false)
    val isValidLoginNotice : LiveData<Boolean>
        get() = _isValidLoginNotice

    // 로그인
    fun postLoginInfo(){
        viewModelScope.launch {
            try{
                val loginInfo = RequestLogin(emailTxt.value.toString(),passwordTxt.value.toString())
                signRepository.postLoginInfo(loginInfo).let {
                    AfumeApplication.prefManager.userIdx = it.userIdx
                    AfumeApplication.prefManager.userEmail = emailTxt.value.toString()
                    AfumeApplication.prefManager.userPassword = passwordTxt.value.toString()
                    AfumeApplication.prefManager.userNickname = it.nickname
                    AfumeApplication.prefManager.userGender = it.gender
                    AfumeApplication.prefManager.userAge = it.birth
                    AfumeApplication.prefManager.accessToken = "Bearer "+it.token
                    AfumeApplication.prefManager.refreshToken = "Bearer "+it.refreshToken
                }

                _isValidLogin.postValue(true)
                _isValidLoginNotice.postValue(false)

                Log.d("로그인 통신 성공", "")
            }catch (e : HttpException){
                _isValidLogin.postValue(false)
                _isValidLoginNotice.postValue(true)
                when (e.response()?.code()) {
                    400 -> { // 중복되는 값 존재
                        Log.d("로그인 통신 실패 : ", e.message())
                    }
                    401 -> { // 잘못된 비밀번호, 아이디 존재 x
                        Log.d("로그인 통신 실패 : ", e.message())
                    }
                }
            }
        }
    }
}