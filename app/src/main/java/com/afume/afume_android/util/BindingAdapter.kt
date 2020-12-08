package com.afume.afume_android.util

import android.widget.ImageView
import com.afume.afume_android.R
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setLikeList")
    fun ImageView.setLikeList(status: Int) {
        if (status == 0) {
            setImageResource(R.drawable.favorite_inactive)
        } else {
            setImageResource(R.drawable.favorite_active)
        }
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, res: Int?) {
        Glide.with(view.context)
            .load(res)
            .into(view)

    }
}