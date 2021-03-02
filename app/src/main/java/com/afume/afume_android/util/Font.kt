package com.afume.afume_android.util

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.afume.afume_android.R

fun TextView.setTextNotoSansBold(isBold:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.notosans_bold else R.font.notosans_regular)
}

fun TextView.setSelectedGenderTxt(isSelected:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isSelected) R.font.nanummyeongjo_extrabold else R.font.nanummyeongjo)
    this.setTextColor(ResourcesCompat.getColor(this.resources, if(isSelected) R.color.primary_black else R.color.gray_cd, null))
}
