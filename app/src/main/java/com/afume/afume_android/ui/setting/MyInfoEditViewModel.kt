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

    // 안내문 내용
    val nickNotice = MutableLiveData<String>()

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
}