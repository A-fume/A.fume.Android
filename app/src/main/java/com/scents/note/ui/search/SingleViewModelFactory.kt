package com.scents.note.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SingleViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: SingleViewModelFactory? = null
        fun getInstance() = instance ?: synchronized(SingleViewModelFactory::class.java) {
            instance ?: SingleViewModelFactory().also { instance = it }
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel.getInstance()
                else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T
}