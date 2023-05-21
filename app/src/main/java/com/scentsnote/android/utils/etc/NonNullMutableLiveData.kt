package com.scentsnote.android.utils.etc

import androidx.lifecycle.MutableLiveData

class NonNullMutableLiveData<T : Any>(defaultValue: T) : MutableLiveData<T>() {

    init {
        value = defaultValue
    }

    override fun getValue()  = super.getValue()!!
}