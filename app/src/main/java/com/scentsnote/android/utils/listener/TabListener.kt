package com.scentsnote.android.utils.listener

import com.google.android.material.tabs.TabLayout
import com.scentsnote.android.utils.extension.changeTabsFont

class TabSelectedListener(private val tabView: TabLayout) : TabLayout.OnTabSelectedListener{
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let{
            tabView.changeTabsFont(it)
        }
    }

}