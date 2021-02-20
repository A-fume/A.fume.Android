package com.afume.afume_android.ui.survey

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.SeriesInfo

object SurveyBinding {
    @JvmStatic
    @BindingAdapter("setPerfumeList")
    fun setPerfumeList(recyclerView: RecyclerView, list : MutableList<PerfumeInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as CircleRecyclerViewAdapter){
            list?.let { setPerfumeData(list)
            Log.e("setList",perfumeData.toString())}
        }
    }
    @JvmStatic
    @BindingAdapter("setSeriesList")
    fun setSeriesList(recyclerView: RecyclerView, list : MutableList<SeriesInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as CircleRecyclerViewAdapter){
            list?.let { setSeriesData(list)
                Log.e("setList",perfumeData.toString())}
        }
    }
}