package com.afume.afume_android.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFilterBinding
    private lateinit var filterViewPagerAdapter: FilterViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()

        initViewPager()
        setUpTabWithViewPager()
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up)


    }
    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)
        binding.lifecycleOwner=this
    }
    private fun initViewPager(){
        filterViewPagerAdapter= FilterViewPagerAdapter(supportFragmentManager)
        filterViewPagerAdapter.fragments= listOf(
            FilterIncenseSeriesFragment(),
            FilterBrandFragment(),
            FilterKeywordFragment()
        )
        binding.vpFilter.adapter=filterViewPagerAdapter
    }
    private fun setUpTabWithViewPager(){
        binding.tabFilter.setupWithViewPager(binding.vpFilter)
        binding.tabFilter.apply {
            val tab1 = getTabAt(0)
            tab1?.text="계열"

            val badge1 = tab1?.orCreateBadge
            badge1?.number=4

            getTabAt(1)?.text="브랜드"
            getTabAt(2)?.text="키워드"
        }
    }


}