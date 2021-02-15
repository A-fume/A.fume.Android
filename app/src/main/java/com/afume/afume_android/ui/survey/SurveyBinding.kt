package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.PerfumeInfo

object SurveyBinding {
    @JvmStatic
    @BindingAdapter("setListItem")
    fun setListItem(recyclerView: RecyclerView, list : MutableList<PerfumeInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as CircleRecyclerViewAdapter){
            list?.let { setData(list)
            Log.e("setList",data.toString())}
        }
    }
}