package com.scents.note.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FilterViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(FilterViewModel::class.java) -> FilterViewModel.getInstance()
                else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T

    companion object {
        private var instance: FilterViewModelFactory? = null

        fun getInstance() = instance ?: synchronized(FilterViewModelFactory::class.java) {
            instance ?: FilterViewModelFactory().also { instance = it }
        }
    }
}