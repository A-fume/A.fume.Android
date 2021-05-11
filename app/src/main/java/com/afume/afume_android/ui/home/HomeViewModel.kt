package com.afume.afume_android.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.HomeRepository
import java.util.*

class HomeViewModel : ViewModel(){
    private val homeRepository = HomeRepository()

    // 고정값 설정
    val nickTxt = MutableLiveData<String>("")
    val ageTxt = MutableLiveData<String>("")

    fun setUserInfo(){
        if(AfumeApplication.prefManager.userEmail.isNotEmpty()){
            nickTxt.postValue(AfumeApplication.prefManager.userNickname)
            ageTxt.postValue(getAgeGroupInfo().toString() + "대 " + getGenderInfo())
        }
    }

    // 나이 구하기
    private fun getAgeGroupInfo() : Int{
        val age = getYear() - AfumeApplication.prefManager.userAge + 1

        return (age/10)*10
    }

    // 현재 년도 구하기
    private fun getYear(): Int{
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }


    private fun getGenderInfo(): String{
        return if(AfumeApplication.prefManager.userGender == "MAN"){
            "남성"
        }else{
            "여성"
        }
    }
}