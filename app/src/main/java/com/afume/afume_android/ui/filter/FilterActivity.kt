package com.afume.afume_android.ui.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityFilterBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.filter.brand.FilterBrandFragment
import com.afume.afume_android.ui.filter.incense.FilterIncenseSeriesFragment
import com.afume.afume_android.ui.filter.keyword.FilterKeywordFragment
import com.afume.afume_android.util.TabSelectedListener
import com.google.android.material.badge.BadgeDrawable

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    private lateinit var filterViewPagerAdapter: AfumeViewPagerAdapter
    private val filterViewModel: FilterViewModel by viewModels(){
        FilterViewModelFactory.getInstance()
    }

    private lateinit var seriesBadge: BadgeDrawable
    private lateinit var brandBadge: BadgeDrawable
    private lateinit var keywordBadge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        initViewPager()
        setUpTabWithViewPager()
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)


        observeViewModel()

        binding.btnFilterApply.setOnClickListener {
            sendFilter()
        }
        binding.toolbarFilter.toolbarBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_down, R.anim.slide_down)
        }

        binding.toolbarFilter.toolbar = R.drawable.icon_btn_cancel
        binding.toolbarFilter.toolbartxt = "필터"

    }

    override fun onResume() {
        super.onResume()


    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        binding.lifecycleOwner = this
        binding.filterVm = filterViewModel

    }

    private fun initViewPager() {
        filterViewPagerAdapter = AfumeViewPagerAdapter(supportFragmentManager)
        filterViewPagerAdapter.fragments = listOf(
            FilterIncenseSeriesFragment(),
            FilterBrandFragment(),
            FilterKeywordFragment()
        )
        binding.vpFilter.adapter = filterViewPagerAdapter
    }

    private fun setUpTabWithViewPager() {
        binding.tabFilter.setupWithViewPager(binding.vpFilter)
        binding.tabFilter.apply {
            val tab1 = getTabAt(0)
            tab1?.text = "계열"

            //observe 가 관찰해야함
            seriesBadge = tab1!!.orCreateBadge
            seriesBadge.backgroundColor = ContextCompat.getColor(this.context, R.color.black)
//            badge1?.number=0
//            if(badge1?.number==0) badge1.isVisible=false

            getTabAt(1)?.text = "브랜드"
            brandBadge = getTabAt(1)!!.orCreateBadge
            brandBadge.backgroundColor = ContextCompat.getColor(this.context, R.color.black)

            getTabAt(2)?.text = "키워드"
            keywordBadge = getTabAt(2)!!.orCreateBadge
            keywordBadge.backgroundColor = ContextCompat.getColor(this.context, R.color.black)
        }
        binding.tabFilter.addOnTabSelectedListener(TabSelectedListener(binding.tabFilter))
    }

    private fun observeViewModel() {
        filterViewModel.badgeCount.observe(this, Observer<MutableList<Int>>() {

            it[0].apply { isVisibleBadge(seriesBadge, this) }
            it[1].apply { isVisibleBadge(brandBadge, this) }
            it[2].apply { isVisibleBadge(keywordBadge, this) }

            Log.e("옵저버 뱃지", it.toString())
        })

        filterViewModel.applyBtn.observe(this, Observer {
            binding.btnFilterApply.apply {
                if (it == 0) {
                    text = "적용"
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                } else {
                    text = "적용($it)"
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.black))
                }
            }
        })
    }

    private fun sendFilter(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("flag",1)
        intent.putExtra("filter",filterViewModel.sendSelectFilter())
        startActivity(intent)
    }


}

fun isVisibleBadge(badge: BadgeDrawable, count: Int) {
    if (count == 0) {
        badge.isVisible = false
    } else {
        badge.number = count
        badge.isVisible = true
    }
}