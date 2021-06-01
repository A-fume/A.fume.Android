package com.afume.afume_android.util

import android.widget.CheckedTextView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.afume.afume_android.R

fun TextView.setTextNotoSansBold(isBold:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.notosans_bold else R.font.notosans_regular)
}

fun TextView.setSelectedSeekBarTxt(isBold: Boolean){
    if(isBold){
        this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_bold)
        this.setTextColor(ContextCompat.getColor(this.context, R.color.primary_black))
    }else{
        this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_regular)
        this.setTextColor(ContextCompat.getColor(this.context, R.color.dark_gray_7d))
    }
}

fun CheckedTextView.setSelectedSeasonBtn(isChecked: Boolean){
    if(isChecked){
        this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_bold)
        this.setTextColor(ContextCompat.getColor(this.context, R.color.white))
    }else{
        this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_regular)
        this.setTextColor(ContextCompat.getColor(this.context, R.color.dark_gray_7d))
    }
}