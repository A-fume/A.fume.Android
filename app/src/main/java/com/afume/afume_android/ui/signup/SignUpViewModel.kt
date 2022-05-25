package com.afume.afume_android.ui.signup

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.SignRepository
import com.afume.afume_android.data.vo.request.RequestRegister
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.regex.Pattern

class SignUpViewModel()  : ViewModel() {
    private val signRepository = SignRepository()

    // 입력 내용
    val emailTxt = MutableLiveData<String>("")
    val nickTxt = MutableLiveData<String>("")
    val passwordTxt = MutableLiveData<String>("")
    val againPasswordTxt = MutableLiveData<String>("")
    val ageTxt = MutableLiveData<String>("1990")

    // 안내문 내용
    val emailNotice = MutableLiveData<String>()
    val nickNotice = MutableLiveData<String>()

    // 이메일 형식 검사 - 하단 안내문
    private val _isValidEmailNotice = MutableLiveData<Boolean>(false)
    val isValidEmailNotice : LiveData<Boolean>
        get() = _isValidEmailNotice

    // 이메일 중복확인 버튼 노출 여부
    private val _isValidEmailBtn = MutableLiveData<Boolean>(false)
    val isValidEmailBtn : LiveData<Boolean>
        get() = _isValidEmailBtn

    // 이메일 중복확인
    private val _isValidEmail = MutableLiveData<Boolean>(false)
    val isValidEmail : LiveData<Boolean>
        get() = _isValidEmail

    // 이메일 입력 실시간 확인
    fun inputEmail(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 0L)
        Handler().postDelayed({ checkEmptyEmail() }, 0L)

        resetValidateEmail()
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        emailNotice.value = "이메일 형식이 맞지 않습니다."
        _isValidEmailNotice.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.value.toString()).matches()

        if(_isValidEmailNotice.value == true){
            _isValidEmailBtn.postValue(false)
        }else{
            _isValidEmailBtn.postValue(true)
        }
    }

    // 이메일 빈칸확인
    private fun checkEmptyEmail(){
        if(emailTxt.value?.length == 0){
            _isValidEmailNotice.postValue(false)
        }
    }

    private fun resetValidateEmail(){
        if(_isValidEmail.value == true){
            _isValidEmail.postValue(false)
            _isValidEmailBtn.postValue(true)
        }
    }

    // 이메일 중복확인
    fun getValidateEmail(){
        viewModelScope.launch{
            try{
                _isValidEmail.value = signRepository.getValidateEmail(emailTxt.value.toString())

                if(_isValidEmail.value == true){
                    _isValidEmailNotice.postValue(false)
                    _isValidEmailBtn.postValue(false)

                    if(!_nickForm.value!!) _nickForm.postValue(true)
                }
            } catch (e : HttpException){
                when(e.response()?.code()){
                    409 -> { // 사용중인 이메일
                        emailNotice.value = "이미 사용 중인 이메일입니다."
                        _isValidEmail.postValue(false)
                        _isValidEmailNotice.postValue(true)
                        _isValidEmailBtn.postValue(true)
                    }
                }
            }
        }
    }

    // 닉네임 형식 검사 - 하단 안내문
    private val _isValidNickNotice = MutableLiveData<Boolean>(false)
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 닉네임 중복확인 버튼 노출 여부
    private val _isValidNickBtn = MutableLiveData<Boolean>(false)
    val isValidNickBtn : LiveData<Boolean>
        get() = _isValidNickBtn

    // 닉네임 중복확인
    private val _isValidNick = MutableLiveData<Boolean>(false)
    val isValidNick : LiveData<Boolean>
        get() = _isValidNick

    // 닉네임 입력란 노출
    private val _nickForm = MutableLiveData<Boolean>(false)
    val nickForm : LiveData<Boolean>
        get() = _nickForm

    // 닉네임 입력 실시간 확인
    fun inputNick(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkNickForm()},0L)
        Handler().postDelayed({ checkEmptyNick()},0L)

        resetValidateNick()
    }

    // 닉네임 형식 확인
    private fun checkNickForm() {
        nickNotice.value = "특수문자는 사용하실 수 없습니다."
        val nickPattern: Pattern = Pattern.compile("[ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z0-9]{0,30}")

        _isValidNickNotice.value = !nickPattern.matcher(nickTxt.value.toString()).matches()

        if(_isValidNickNotice.value == true){
            _isValidNickBtn.postValue(false)
        }else{
            _isValidNickBtn.postValue(true)
        }
    }

    // 닉네임 빈칸확인
    private fun checkEmptyNick(){
        if(nickTxt.value?.length == 0){
            _isValidNickBtn.postValue(false)
            _isValidNickNotice.postValue(false)
        }
    }

    private fun resetValidateNick(){
        if(_isValidNick.value!!){
            _isValidNick.postValue(false)
            _isValidNickBtn.postValue(true)
        }
    }

    // 닉네임 중복확인
    fun getValidateNickname(){
        viewModelScope.launch {
            try{
                _isValidNick.value = signRepository.getValidateNickname(nickTxt.value.toString())

                if(_isValidNick.value == true){
                    _isValidNickNotice.postValue(false)
                    _isValidNickBtn.postValue(false)
                }
            }catch (e : HttpException){
                when(e.response()?.code()){
                    409 -> { // 사용중인 닉네임
                        nickNotice.value = "이미 등록된 닉네임입니다."
                        _isValidNick.postValue(false)
                        _isValidNickNotice.postValue(true)
                        _isValidNickBtn.postValue(true)
                    }
                }
            }
        }
    }

    // 개인정보 수집 버튼 체크
    private val _privacyBtn = MutableLiveData<Boolean>(false)
    val privacyBtn : LiveData<Boolean>
        get() = _privacyBtn

    fun setPrivacyBtn(){
        _privacyBtn.value = !_privacyBtn.value!!
    }

    // 다음 버튼 노출 - 이메일
    private val _emailNextBtn = MutableLiveData<Boolean>(false)
    val emailNextBtn : LiveData<Boolean>
        get() = _emailNextBtn

    // 다음 버튼 노출 여부 검사 - 이메일
    fun checkNextBtn(){
        _emailNextBtn.postValue(isValidEmail.value == true && _isValidNick.value == true && _privacyBtn.value == true)
    }

    // 비밀번호 형식 검사 - 하단 안내문
    private val _isValidPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 비밀번호 형식 검사 - 우측 이미지
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 비밀번호 일치 검사 - 하단 안내문
    private val _isValidAgainNotice = MutableLiveData<Boolean>(false)
    val isValidAgainNotice : LiveData<Boolean>
        get() = _isValidAgainNotice

    // 비밀번호 일치 검사 - 우측 이미지
    private val _isValidAgain = MutableLiveData<Boolean>(false)
    val isValidAgain : LiveData<Boolean>
        get() = _isValidAgain

    // 비밀번호 확인란 노출
    private val _againPasswordForm = MutableLiveData<Boolean>(false)
    val againPasswordForm : LiveData<Boolean>
        get() = _againPasswordForm
    // 비밀번호 입력 실시간 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkPasswordForm() }, 2000L)
    }

    // 비밀번호 자리수 확인
    private fun checkPasswordForm() {
        when(passwordTxt.value?.length){
            0 -> _isValidPasswordNotice.postValue(false)
            in 1..3 -> {
                _isValidPasswordNotice.postValue(true)
                _isValidPassword.postValue(false)
            }
            else -> {
                _isValidPasswordNotice.postValue(false)
                _isValidPassword.postValue(true)

                if(!_againPasswordForm.value!!) _againPasswordForm.postValue(true)
            }
        }
        checkAgainForm()
    }

    // 확인 비밀번호 실시간 확인
    fun inputAgainPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkAgainForm() }, 0L)
    }

    // 비밀번호 일치 확인
    private fun checkAgainForm(){
        when {
            againPasswordTxt.value.toString() == passwordTxt.value.toString() -> {
                _isValidAgainNotice.postValue(false)
                _isValidAgain.postValue(true)

                _passwordNextBtn.postValue(true)
            }
            againPasswordTxt.value?.length == 0 -> {
                _isValidAgainNotice.postValue(false)
            }
            else -> {
                _isValidAgainNotice.postValue(true)
                _isValidAgain.postValue(false)
            }
        }

    }

    // 다음 버튼 노출 - 비밀번호
    private val _passwordNextBtn = MutableLiveData<Boolean>(false)
    val passwordNextBtn : LiveData<Boolean>
        get() = _passwordNextBtn

    // 다음 버튼 노출 여부 검사 - 비밀번호
    fun checkPasswordNextBtn(){
        _passwordNextBtn.postValue(_isValidPassword.value == true && _isValidAgain.value == true)
    }

    // 성별 선택 여부 - 남자
    private val _isCheckMan = MutableLiveData<Boolean>(false)
    val isCheckMan : LiveData<Boolean>
        get() = _isCheckMan

    // 성별 선택 여부 - 여자
    private val _isCheckWoman = MutableLiveData<Boolean>(false)
    val isCheckWoman : LiveData<Boolean>
        get() = _isCheckWoman

    // 다음 버튼 노출 - 성별
    private val _genderNextBtn = MutableLiveData<Boolean>(false)
    val genderNextBtn : LiveData<Boolean>
        get() = _genderNextBtn

    // 남자 버튼 클릭
    fun onClickManBtn(){
        _isCheckMan.postValue(true)
        _isCheckWoman.postValue(false)

        _genderNextBtn.postValue(true)
    }

    // 여자 버튼 클릭
    fun onClickWomanBtn(){
        _isCheckMan.postValue(false)
        _isCheckWoman.postValue(true)

        _genderNextBtn.postValue(true)
    }

    // data 저장
    fun addUserInfo(info : String){
        when(info){
            "email" -> {
                AfumeApplication.prefManager.userEmail = emailTxt.value.toString()
                AfumeApplication.prefManager.userNickname = nickTxt.value.toString()
            }
            "password" -> {
                AfumeApplication.prefManager.userPassword = passwordTxt.value.toString()
            }
            "gender" -> {
                if(_isCheckMan.value == true){
                    AfumeApplication.prefManager.userGender = "MAN"
                }else{
                    AfumeApplication.prefManager.userGender = "WOMAN"
                }
            }
            "age" -> {
                AfumeApplication.prefManager.userAge = ageTxt.value!!.toInt()
            }
        }
    }

    // 회원가입
    private val _isValidRegister = MutableLiveData<Boolean>(false)
    val isValidRegister : LiveData<Boolean>
        get() = _isValidRegister

    // 회원가입
    fun postRegister() {
        viewModelScope.launch {
            try {
                val registerInfo = RequestRegister(
                    AfumeApplication.prefManager.userEmail,
                    AfumeApplication.prefManager.userNickname,
                    AfumeApplication.prefManager.userPassword,
                    AfumeApplication.prefManager.userGender,
                    AfumeApplication.prefManager.userAge
                )
                signRepository.postRegisterInfo(registerInfo).let {
                    Log.d("회원 가입 통신 성공 : ", it.toString())
                    AfumeApplication.prefManager.userIdx = it.userIdx
                    AfumeApplication.prefManager.accessToken = "Bearer "+it.token
                    AfumeApplication.prefManager.refreshToken = "Bearer "+it.refreshToken
                    _isValidRegister.postValue(true)
                    AfumeApplication.prefManager.userSurvey = true
                }
            } catch (e: HttpException) {
                _isValidRegister.postValue(false)
            }
        }
    }
}