package com.afume.afume_android.ui.search

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.bumptech.glide.Glide

object SearchBinding {
    @JvmStatic
    @BindingAdapter("bindingDrawable")
    fun changeDrawable(imageView: ImageView, drawable: String) {
        Glide.with(imageView.context)
            .load(drawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setFilterData")
    fun setFilterData(recyclerView: RecyclerView, list: MutableList<FilterInfoP>?) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as SelectedFilterRecyclerViewAdapter) {
            list?.let {
                setData(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setResultPerfume")
    fun setResultPerfume(recyclerView: RecyclerView, list: MutableList<PerfumeInfo>?){
        if(recyclerView.adapter != null) with(recyclerView.adapter as DefaultPerfumeRecyclerViewAdapter){
            list?.let { setData(list) }
        }
    }

    @JvmStatic
    @BindingAdapter("checked")
    fun setCheck(imageView: ImageView, boolean: Boolean){
        imageView.isSelected=boolean
    }
}

