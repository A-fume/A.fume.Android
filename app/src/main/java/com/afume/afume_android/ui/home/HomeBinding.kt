package com.afume.afume_android.ui.home

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.NewPerfumeItem
import com.afume.afume_android.ui.home.adapter.MoreNewListAdapter

object HomeBinding {
    @JvmStatic
    @BindingAdapter("setNewPerfumeList")
    fun setNewPerfumeList(recyclerView: RecyclerView, list : MutableList<NewPerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as MoreNewListAdapter){
            list?.let {
                setNewPerfume(list)
                Log.d("명","되냐")
                Log.d("setNewPerfumeList",data.toString())}
        }
    }
}