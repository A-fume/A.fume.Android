package com.afume.afume_android.ui.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.afume.afume_android.data.vo.response.HomePerfumeItem
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
import com.afume.afume_android.ui.home.adapter.*

object HomeBinding {
    @JvmStatic
    @BindingAdapter("setRecommendPerfumeList")
    fun setRecommendPerfumeList(viewPager2: ViewPager2, list : MutableList<RecommendPerfumeItem>?){
        if(viewPager2.adapter!=null) with(viewPager2.adapter as RecommendListAdapter){
            list?.let {
                setRecommendPerfume(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setCommonPerfumeList")
    fun setCommonPerfumeList(recyclerView: RecyclerView, list : MutableList<HomePerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as PopularListAdapter){
            list?.let {
                setCommonPerfume(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setRecentPerfumeList")
    fun setRecentPerfumeList(recyclerView: RecyclerView, list : MutableList<HomePerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as RecentListAdapter){
            list?.let {
                setRecentPerfume(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setNewPerfume")
    fun setNewPerfume(recyclerView: RecyclerView, list : MutableList<HomePerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as NewListAdapter){
            list?.let {
                setNewPerfume(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setNewPerfumeList")
    fun setNewPerfumeList(recyclerView: RecyclerView, list : MutableList<HomePerfumeItem>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as MoreNewListAdapter){
            list?.let {
                setNewPerfume(list)
            }
        }
    }
}