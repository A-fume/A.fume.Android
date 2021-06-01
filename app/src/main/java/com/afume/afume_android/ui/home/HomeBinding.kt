package com.afume.afume_android.ui.home

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.afume.afume_android.data.vo.response.NewPerfumeItem
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
import com.afume.afume_android.ui.home.adapter.MoreNewListAdapter
import com.afume.afume_android.ui.home.adapter.RecommendListAdapter

object HomeBinding {
    @JvmStatic
    @BindingAdapter("setRecommendPerfumeList")
    fun setRecommendPerfumeList(viewPager2: ViewPager2, list : MutableList<RecommendPerfumeItem>?){
        if(viewPager2.adapter!=null) with(viewPager2.adapter as RecommendListAdapter){
            list?.let {
                setRecommendPerfume(list)
                Log.d("명","되냐")
                Log.d("setRecommendPerfumeList",data.toString())}
        }
    }

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