package com.afume.afume_android.ui.setting

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.AfumeApplication
import java.util.regex.Pattern

class MyInfoEditViewModel : ViewModel() {
    // 입력 내용
    val nickTxt = MutableLiveData<String>("")
    val ageTxt = MutableLiveData<String>("")
    val passwordTxt = MutableLiveData<String>("")
    val newPasswordTxt = MutableLiveData<String>("")
    val againPasswordTxt = MutableLiveData<String>("")

    // 안내문 내용
    val nickNotice = MutableLiveData<String>()
    val passwordNotice = MutableLiveData<String>()

    // 자동입력
    fun checkMyInfo(){
        if(AfumeApplication.prefManager.userEmail.isNotEmpty()){
            nickTxt.postValue(AfumeApplication.prefManager.userNickname)
            checkGenderInfo(AfumeApplication.prefManager.userGender)
            ageTxt.postValue(AfumeApplication.prefManager.userAge.toString())
        }
    }

    private fun checkGenderInfo(gender : String){
        if(gender == "MAN"){
            _isCheckMan.postValue(true)
            _isCheckWoman.postValue(false)
        }else{
            _isCheckMan.postValue(false)
            _isCheckWoman.postValue(false)
        }
    }

    // 닉네임 형식 검사 - 하단 안내문
    private val _isValidNickNotice = MutableLiveData<Boolean>(false)
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 닉네임 중복확인 버튼 노출 여부
    private val _isValidNickBtn = MutableLiveData<Boolean>(true)
    val isValidNickBtn : LiveData<Boolean>
        get() = _isValidNickBtn

    // 닉네임 중복확인
    private val _isValidNick = MutableLiveData<Boolean>(false)
    val isValidNick : LiveData<Boolean>
        get() = _isValidNick

    // 닉네임 입력 실시간 확인
    fun inputNickname(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkNickForm() }, 0L)

        resetValidateNick()
    }

    // 닉네임 형식 확인
    private fun checkNickForm() {
        nickNotice.value = "특수문자는 사용하실 수 없습니다."
        val nickPattern: Pattern = Pattern.compile("[ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z0-9]{0,30}")

        _isValidNickNotice.value = !nickPattern.matcher(nickTxt.value.toString()).matches()

        checkEmptyNick()
        checkChangeNick()
    }

    // 닉네임 빈칸확인
    private fun checkEmptyNick(){
        if(nickTxt.value?.length == 0){
            _isValidNickBtn.postValue(false)
            _isValidNickNotice.postValue(false)
        }else{
            _isValidNickBtn.postValue(true)
        }
    }

    // 닉네임 변경확인
    private fun checkChangeNick(){
        if(nickTxt.value == AfumeApplication.prefManager.userNickname){
            _isValidNickBtn.postValue(false)
        }else{
            _isValidNickBtn.postValue(true)
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
        if(nickTxt.value == "test4"){
            _isValidNick.postValue(false)
            _isValidNickNotice.postValue(true)
            _isValidNickBtn.postValue(true)
        }else{
            _isValidNick.postValue(true)
            _isValidNickNotice.postValue(false)
            _isValidNickBtn.postValue(false)
        }
    }

    // 성별 선택 여부 - 남자
    private val _isCheckMan = MutableLiveData<Boolean>(false)
    val isCheckMan : LiveData<Boolean>
        get() = _isCheckMan

    // 성별 선택 여부 - 여자
    private val _isCheckWoman = MutableLiveData<Boolean>(false)
    val isCheckWoman : LiveData<Boolean>
        get() = _isCheckWoman

    // 남자 버튼 클릭
    fun onClickManBtn(){
        _isCheckMan.postValue(true)
        _isCheckWoman.postValue(false)
    }

    // 여자 버튼 클릭
    fun onClickWomanBtn(){
        _isCheckMan.postValue(false)
        _isCheckWoman.postValue(true)
    }

    // 본인확인 검사 - 하단 안내문
    private val _isValidPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 본인확인 버튼 노출 여부
    private val _isValidPasswordBtn = MutableLiveData<Boolean>(false)
    val isValidPasswordBtn : LiveData<Boolean>
        get() = _isValidPasswordBtn

    // 본인확인
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 새비밀번호 입력란 노출
    private val _newPasswordForm = MutableLiveData<Boolean>(false)
    val newPasswordForm : LiveData<Boolean>
        get() = _newPasswordForm

    // 비밀번호 입력 실시간 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkPasswordForm() }, 0L)

        resetValidPassword()
    }

    private fun resetValidPassword(){
        if(_isValidPassword.value!!){
            _isValidPassword.postValue(false)
            _isValidPasswordBtn.postValue(true)
        }
    }

    // 비밀번호 자리수 확인
    private fun checkPasswordForm() {
        passwordNotice.value = "4자리 이상 입력해주세요."
        when(passwordTxt.value?.length){
            0 -> _isValidPasswordNotice.postValue(false)
            in 1..3 -> {
                _isValidPasswordNotice.postValue(true)
            }
            else -> {
                _isValidPasswordNotice.postValue(false)
                _isValidPasswordBtn.postValue(true)
            }
        }
    }

    fun checkValidPassword(){
        passwordNotice.value = "비밀번호를 다시 입력해주세요."
        if(passwordTxt.value == AfumeApplication.prefManager.userPassword){
            _isValidPassword.postValue(true)
            _isValidPasswordBtn.postValue(false)
            _isValidPasswordNotice.postValue(false)

            if(!_newPasswordForm.value!!) _newPasswordForm.postValue(true)
        }else{
            _isValidPassword.postValue(false)
            _isValidPasswordBtn.postValue(true)
            _isValidPasswordNotice.postValue(true)
        }
    }

    // 새비밀번호 형식 검사 - 하단 안내문
    private val _isValidNewPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidNewPasswordNotice : LiveData<Boolean>
        get() = _isValidNewPasswordNotice

    // 새비밀번호 형식 검사 - 우측 이미지
    private val _isValidNewPassword = MutableLiveData<Boolean>(false)
    val isValidNewPassword : LiveData<Boolean>
        get() = _isValidNewPassword


    // 새비밀번호 실시간 확인
    fun inputNewPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkNewPasswordForm() }, 1000L)
    }

    // 새비밀번호 자리수 확인
    private fun checkNewPasswordForm(){
        when(newPasswordTxt.value?.length){
            0 -> _isValidNewPasswordNotice.postValue(false)
            in 1..3 -> {
                _isValidNewPasswordNotice.postValue(true)
                _isValidNewPassword.postValue(false)
            }
            else -> {
                _isValidNewPasswordNotice.postValue(false)
                _isValidNewPassword.postValue(true)
            }
        }
        checkAgainForm()
    }

    // 새비밀번호 일치 검사 - 하단 안내문
    private val _isValidAgainPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidAgainPasswordNotice : LiveData<Boolean>
        get() = _isValidAgainPasswordNotice

    // 새비밀번호 일치 검사 - 우측 이미지
    private val _isValidAgainPassword = MutableLiveData<Boolean>(false)
    val isValidAgainPassword : LiveData<Boolean>
        get() = _isValidAgainPassword

    // 새비밀번호 일치 실시간 확인
    fun inputAgainPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkAgainForm() }, 1000L)
    }

    private fun checkAgainForm(){
        when{
            newPasswordTxt.value.toString() == againPasswordTxt.value.toString() -> {
                _isValidAgainPasswordNotice.postValue(false)
                _isValidAgainPassword.postValue(true)
            }
            againPasswordTxt.value?.length == 0 -> {
                _isValidAgainPasswordNotice.postValue(false)
            }
            else -> {
                _isValidAgainPasswordNotice.postValue(true)
                _isValidAgainPassword.postValue(false)
            }
        }
    }

    // 다음 버튼 노출 - 비밀번호
    private val _passwordEditCompleteBtn = MutableLiveData<Boolean>(false)
    val passwordEditCompleteBtn : LiveData<Boolean>
        get() = _passwordEditCompleteBtn

    // 다음 버튼 노출 여부 검사 - 비밀번호
    fun checkPasswordNextBtn(){
        _passwordEditCompleteBtn.postValue(_isValidNewPassword.value == true && _isValidAgainPassword.value == true)
    }
}