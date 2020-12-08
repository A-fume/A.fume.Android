package com.afume.afume_android.search

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bindingDrawable")
fun changeDrawable(imageView: ImageView, drawable: Int) {
    Glide.with(imageView.context)
        .load(drawable)
        .into(imageView)
}