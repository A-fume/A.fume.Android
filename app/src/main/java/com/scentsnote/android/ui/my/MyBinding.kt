package com.scentsnote.android.ui.my

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.ResponseMyPerfume
import com.scentsnote.android.data.vo.response.WishPerfume
import com.scentsnote.android.ui.my.myperfume.MyPerfumeRecyclerViewAdapter
import com.scentsnote.android.ui.my.wishlist.WishListAdapter
import com.scentsnote.android.utils.etc.Log

object MyBinding {
    @JvmStatic
    @BindingAdapter("setWishList")
    fun setPerfumeList(recyclerView: RecyclerView, list : MutableList<WishPerfume>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as WishListAdapter){
            list?.let { setWishListData(list)
                Log.e("setWishListData",data.toString())}
        }
    }
    @JvmStatic
    @BindingAdapter("setMyPerfumeList")
    fun setMyPerfumeList(recyclerView : RecyclerView, list: MutableList<ResponseMyPerfume>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as MyPerfumeRecyclerViewAdapter){
            list.let{
                setMyPerfumeListData(list)
                Log.e("setWishListData",data.toString())
            }
        }
    }
}