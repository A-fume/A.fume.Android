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

    @JvmStatic
    @BindingAdapter("setManIcon")
    fun ImageView.setManIcon(status: Int) {
        if (status == 0) {
            setImageResource(R.drawable.btn_man_inactive)
        } else {
            setImageResource(R.drawable.btn_man_active)
        }
    }

    @JvmStatic
    @BindingAdapter("setWomanIcon")
    fun ImageView.setWomanIcon(status: Int) {
        if (status == 0) {
            setImageResource(R.drawable.btn_woman_inactive)
        } else {
            setImageResource(R.drawable.btn_woman_active)
        }
    }
}