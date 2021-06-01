package com.afume.afume_android.util

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.afume.afume_android.R
import com.bumptech.glide.Glide

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
    @BindingAdapter("setLikeList")
    fun ImageView.setLikeList(status: Boolean) {
        if (!status) {
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
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, res: String?) {
        Glide.with(view.context)
            .load(res)
            .into(view)

    }

    @JvmStatic
    @BindingAdapter("setManIcon")
    fun ImageView.setManIcon(status: Boolean) {
        if (status) {
            setImageResource(R.drawable.btn_man_active)
        } else {
            setImageResource(R.drawable.btn_man_inactive)
        }
    }

    @JvmStatic
    @BindingAdapter("setWomanIcon")
    fun ImageView.setWomanIcon(status: Boolean) {
        if (status) {
            setImageResource(R.drawable.btn_woman_active)
        } else {
            setImageResource(R.drawable.btn_woman_inactive)
        }
    }

    @JvmStatic
    @BindingAdapter("setGenderFont")
    fun TextView.setGenderFont(status: Boolean){
        typeface = ResourcesCompat.getFont(this.context, if(status) R.font.nanummyeongjo_extrabold else R.font.nanummyeongjo)
        setTextColor(ResourcesCompat.getColor(this.resources, if(status) R.color.primary_black else R.color.gray_cd, null))
    }

    @JvmStatic
    @BindingAdapter("setWarningFont")
    fun EditText.setWarningFont(status: Boolean){
        setTextColor(ResourcesCompat.getColor(this.resources, if(status) R.color.brick else R.color.primary_black, null))
    }
}