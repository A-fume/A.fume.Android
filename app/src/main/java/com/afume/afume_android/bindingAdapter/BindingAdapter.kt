package com.afume.afume_android.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.afume.afume_android.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setLikeList")
    fun ImageView.setLikeList(status : Int) {
        if (status==0){
            setImageResource(R.drawable.favorite_inactive)
        }else{
            setImageResource(R.drawable.favorite_active)
        }
    }

}