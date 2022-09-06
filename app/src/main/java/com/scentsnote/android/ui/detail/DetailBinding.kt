package com.scentsnote.android.ui.detail

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.RecommendPerfumeItem
import com.scentsnote.android.ui.detail.info.SimilarListAdapter

object DetailBinding {
    @JvmStatic
    @BindingAdapter("setSimilarPerfumeList")
    fun setSimilarPerfumeList(recyclerView: RecyclerView, list : MutableList<RecommendPerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as SimilarListAdapter){
            list?.let {
                setSimilarPerfume(list)
            }
        }
    }
}