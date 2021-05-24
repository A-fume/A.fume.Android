package com.afume.afume_android.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.HomeRepository
import com.afume.afume_android.data.vo.response.NewPerfumeItem
import kotlinx.coroutines.launch
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

    private val _newPerfumeList : MutableLiveData<MutableList<NewPerfumeItem>> = MutableLiveData()
    val newPerfumeList : LiveData<MutableList<NewPerfumeItem>>
        get() = _newPerfumeList

    init {
        viewModelScope.launch {
            _newPerfumeList.value = homeRepository.getNewPerfumeList()
            Log.d("명2",_newPerfumeList.value.toString())
        }
    }

//    fun getNewPerfumeList(){
//        viewModelScope.launch {
//            _newPerfumeList.value = homeRepository.getNewPerfumeList()
//        }
//    }
}