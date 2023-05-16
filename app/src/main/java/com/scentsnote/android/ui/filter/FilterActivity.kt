package com.scentsnote.android.ui.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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
import com.scentsnote.android.viewmodel.filter.FilterViewModel
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.changeTabsFont
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.listener.TabSelectedListener

/**
 * 향수 검색 - 필터
 *
 * 계열, 브랜드, 키워드 필터 제공
 */
class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var filterViewPagerAdapter: ScentsNoteViewPagerAdapter
    private val filterViewModel: FilterViewModel by viewModels()
    private val filterCategoryList = FilterCategory.values()

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
        binding.tabFilter.apply {
            setupWithViewPager(binding.vpFilter)
            filterCategoryList.forEachIndexed { index, filterCategory ->
                val tab = getTabAt(index)
                tab?.text = filterCategory.nameText
                tab?.orCreateBadge?.backgroundColor = context.getColor(R.color.black)
            }
            addOnTabSelectedListener(TabSelectedListener(binding.tabFilter))
            changeTabsFont(0)
        }
    }

    private fun observeViewModel() {
        filterViewModel.badgeCount.observe(this) { count ->
            filterCategoryList.indices.forEach { idx ->
                val tab = binding.tabFilter.getTabAt(idx)
                tab?.orCreateBadge?.let {
                    updateCategoryBadge(it, count[idx])
                }
            }
        }

        filterViewModel.applyBtn.observe(this) {
            binding.btnFilterApply.text =
                if (it == 0) {
                    "적용"
                } else {
                    "적용($it)"
                }

        }
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

    private fun updateCategoryBadge(badge: BadgeDrawable, count: Int) {
        badge.isVisible = count > 0
        badge.number = count
    }
}