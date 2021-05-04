package com.afume.afume_android.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    // 입력 내용
    val contentsTxt = MutableLiveData<String>("")

}