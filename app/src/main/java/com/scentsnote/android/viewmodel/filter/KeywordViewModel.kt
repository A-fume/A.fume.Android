package com.scentsnote.android.viewmodel.filter

import androidx.lifecycle.ViewModel
import com.scentsnote.android.data.repository.FilterRepository

class KeywordViewModel(
    private val filterRepository: FilterRepository = FilterRepository()
):ViewModel() {
}