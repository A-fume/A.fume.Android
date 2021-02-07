package com.afume.afume_android.util

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.afume.afume_android.R

fun TextView.setTextNotoSansBold(isBold:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.notosans_bold else R.font.notosans_regular)
}
