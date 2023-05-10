package com.scentsnote.android.utils.extension

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import com.google.android.material.tabs.TabLayout

fun TabLayout.changeTabsFont(selectPosition:Int){
    val vg = this. getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (i in 0 until tabsCount){
        val vgTab = vg.getChildAt(i) as ViewGroup
        vgTab.forEachIndexed { index, _ ->
            val tabViewChild = vgTab.getChildAt(index)
            if(tabViewChild is TextView){
                tabViewChild.setTextNotoSansBold(i == selectPosition)
            }
        }
    }
}