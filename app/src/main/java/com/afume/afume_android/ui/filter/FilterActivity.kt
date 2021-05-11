package com.afume.afume_android.ui.filter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityFilterBinding
import com.afume.afume_android.ui.filter.brand.FilterBrandFragment
import com.afume.afume_android.ui.filter.incense.FilterIncenseSeriesFragment
import com.afume.afume_android.ui.filter.keyword.FilterKeywordFragment
import com.afume.afume_android.util.TabSelectedListener
import com.google.android.material.badge.BadgeDrawable

class FilterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFilterBinding
    private lateinit var filterViewPagerAdapter: AfumeViewPagerAdapter
    private  val filterViewModel: FilterViewModel by viewModels()

    private lateinit var badge1:BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        initViewPager()
        setUpTabWithViewPager()
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up)


        observeViewModel()

        binding.btnFilterApply.setOnClickListener{
            filterViewModel.increaseBadgeCount(0)
        }

        binding.toolbarFilter.toolbar=R.drawable.icon_btn_cancel
        binding.toolbarFilter.toolbartxt="필터"

    }
    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)
        binding.lifecycleOwner=this
        binding.filterVm=filterViewModel


    }
    private fun initViewPager(){
        filterViewPagerAdapter=
            AfumeViewPagerAdapter(
                supportFragmentManager
            )
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

            //observe 가 관찰해야함
            badge1 = tab1!!.orCreateBadge
            badge1.backgroundColor=ContextCompat.getColor(this.context, R.color.black)
//            badge1?.number=0
//            if(badge1?.number==0) badge1.isVisible=false

            getTabAt(1)?.text="브랜드"
            getTabAt(2)?.text="키워드"
        }
        binding.tabFilter.addOnTabSelectedListener(TabSelectedListener(binding.tabFilter))
    }

    private fun observeViewModel(){
       filterViewModel.incenseBadgeCount.observe(this, Observer<Int>() {
          if(it==0){
              badge1.isVisible=false
          }
           else{
              badge1.isVisible=true
              badge1.number=it
          }
       })
       filterViewModel.applyBtn.observe(this, Observer {
           if(it==0){
               binding.btnFilterApply.apply {
                   text="적용"
                   setBackgroundColor(ContextCompat.getColor(this.context, R.color.gray_cd))
               }
           }
           else{
               binding.btnFilterApply.apply {
                   text="적용($it)"
                   setBackgroundColor(ContextCompat.getColor(this.context, R.color.black))
               }
           }
       })
    }


}