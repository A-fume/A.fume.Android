package com.afume.afume_android.ui.detail

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.ui.detail.info.DetailKeywordAdapter

object DetailBinding {
    @JvmStatic
    @BindingAdapter("setDetailKeywordList")
    fun setDetailKeywordList(recyclerView: RecyclerView, list : List<String>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as DetailKeywordAdapter){
            list?.let {
                setKeyword(it)}
        }
    }
}