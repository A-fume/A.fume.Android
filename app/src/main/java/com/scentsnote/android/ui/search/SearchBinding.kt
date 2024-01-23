package com.scentsnote.android.ui.search

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.bumptech.glide.Glide
import com.scentsnote.android.utils.etc.Log

object SearchBinding {
    @JvmStatic
    @BindingAdapter("bindingDrawable")
    fun changeDrawable(imageView: ImageView, url: String?) {
       if(url !=null){
           Log.d("url",url)
           Glide.with(imageView.context)
               .load(url)
               .into(imageView)
       }
    }

    @JvmStatic
    @BindingAdapter("setFilterData")
    fun setFilterData(recyclerView: RecyclerView, filter: SendFilter?) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as SelectedFilterRecyclerViewAdapter) {
            filter?.let {
                setData(filter)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setResultPerfume")
    fun setResultPerfume(recyclerView: RecyclerView, list: MutableList<PerfumeInfo>?){
        if(recyclerView.adapter != null) with(recyclerView.adapter as DefaultPerfumeRecyclerViewAdapter){
            list?.let { submitList(list) }
        }
    }

    @JvmStatic
    @BindingAdapter("checked")
    fun setCheck(imageView: ImageView, boolean: Boolean){
        imageView.isSelected=boolean
    }
}

