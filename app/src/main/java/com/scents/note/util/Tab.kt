package com.scents.note.util

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

class TabSelectedListener(private val tabView: TabLayout) : TabLayout.OnTabSelectedListener{
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let{
            tabView?.changeTabsFont(it)
        }
    }

}