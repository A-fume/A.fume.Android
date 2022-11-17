package com.scentsnote.android.ui.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.ActivityFilterBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.brand.FilterBrandFragment
import com.scentsnote.android.ui.filter.incense.FilterIncenseSeriesFragment
import com.scentsnote.android.ui.filter.keyword.FilterKeywordFragment
import com.scentsnote.android.ui.search.SearchHomeFragment.Companion.SEARCH_HOME
import com.google.android.material.badge.BadgeDrawable
import com.scentsnote.android.util.view.BaseActivity
import com.scentsnote.android.util.extension.TabSelectedListener
import com.scentsnote.android.util.extension.changeTabsFont
import com.scentsnote.android.util.extension.setOnSafeClickListener

class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var filterViewPagerAdapter: ScentsNoteViewPagerAdapter
    private val filterViewModel: FilterViewModel by viewModels() {
        FilterViewModelFactory.getInstance()
    }

    private lateinit var seriesBadge: BadgeDrawable
    private lateinit var brandBadge: BadgeDrawable
    private lateinit var keywordBadge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            filterVm = filterViewModel
        }

        setFilterData()
        checkChangeFilter()
        Log.d("filter act filter", filterViewModel.selectedKeywordList.value.toString())

        initViewPager()
        setUpTabWithViewPager()
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)

        observeViewModel()

        binding.btnFilterApply.setOnSafeClickListener {
            sendFilter()
        }
        binding.toolbarFilter.toolbarBtn.setOnSafeClickListener {
            finish()
            overridePendingTransition(R.anim.slide_down, R.anim.slide_down)
        }

        binding.toolbarFilter.toolbar = R.drawable.icon_btn_cancel
        binding.toolbarFilter.toolbartxt = "필터"

    }

    override fun onResume() {
        super.onResume()
        setFilterData()
        checkChangeFilter()

    }

    private fun setFilterData() {
        val fromHome = intent.getIntExtra("from", 0)
        if (fromHome == SEARCH_HOME) {
            filterViewModel.initFilterData()
        }
    }

    private fun initViewPager() {
        filterViewPagerAdapter = ScentsNoteViewPagerAdapter(supportFragmentManager)
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
        binding.tabFilter.changeTabsFont(0)
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
                text = if (it == 0) {
                    "적용"
                } else {
                    "적용($it)"
                }
            }
        })
    }

    private fun sendFilter() {
        Log.d("sendfilter_series", filterViewModel.selectedSeriesMap.value.toString())
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("flag", 1)
        intent.putExtra("filter", filterViewModel.sendSelectFilter())
        startActivity(intent)
    }

    private fun checkChangeFilter() {
        if (intent.getIntExtra("flag", 0) == 5000) {
            val fromSearchResult = intent?.getParcelableExtra<SendFilter>("filter")
            filterViewModel.checkChangeFilter(fromSearchResult)
        }
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