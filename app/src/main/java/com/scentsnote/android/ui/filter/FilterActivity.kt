package com.scentsnote.android.ui.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.ActivityFilterBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.brand.FilterBrandFragment
import com.scentsnote.android.ui.filter.incense.FilterIncenseSeriesFragment
import com.scentsnote.android.ui.filter.keyword.FilterKeywordFragment
import com.scentsnote.android.ui.search.SearchHomeFragment.Companion.SEARCH_HOME
import com.google.android.material.badge.BadgeDrawable
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.changeTabsFont
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.listener.TabSelectedListener
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel

/**
 * 향수 검색 - 필터
 *
 * 계열, 브랜드, 키워드 필터 제공
 */
class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var filterViewPagerAdapter: ScentsNoteViewPagerAdapter
    private val seriesViewModel: FilterSeriesViewModel by viewModels()
    private val brandViewModel: FilterBrandViewModel by viewModels()
    private val keywordViewModel: FilterKeywordViewModel by viewModels()
    private val filterCategoryList = FilterCategory.values()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFilterData()
        checkChangeFilter()

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

    private fun setFilterData() {
        val fromHome = intent.getIntExtra("from", 0)
        if (fromHome == SEARCH_HOME) {
            // TODO
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
        seriesViewModel.selectedCount.observe(this) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Series.index)
            tab?.orCreateBadge?.let {
                updateCategoryBadge(it, count)
            }
            updateApplyBtnText()
        }

        brandViewModel.selectedCount.observe(this) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Brand.index)
            tab?.orCreateBadge?.let {
                updateCategoryBadge(it, count)
            }
            updateApplyBtnText()
        }

        keywordViewModel.selectedCount.observe(this) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Keyword.index)
            tab?.orCreateBadge?.let {
                updateCategoryBadge(it, count)
            }
            updateApplyBtnText()
        }
    }

    private fun updateApplyBtnText() {
        val totalCount = seriesViewModel.count + brandViewModel.count + keywordViewModel.count
        binding.btnFilterApply.text =
            if (totalCount == 0) {
                "적용"
            } else {
                "적용($totalCount)"
            }
    }

    private fun sendFilter() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("flag", 1)
        intent.putExtra("filter", getSelectedFilters())
        startActivity(intent)
    }

    private fun getSelectedFilters(): SendFilter {
        val filterInfoPList = seriesViewModel.getSelectedSeries() +
                brandViewModel.getSelectedBrands() +
                keywordViewModel.getSelectedKeywords()
        return SendFilter(
            filterInfoPList.toMutableList(),
            mutableMapOf() // TODO
        )
    }

    private fun checkChangeFilter() {
        if (intent.getIntExtra("flag", 0) == 5000) {
            val fromSearchResult = intent?.getParcelableExtra<SendFilter>("filter")
            // TODO restore selected filters
        }
    }

    private fun updateCategoryBadge(badge: BadgeDrawable, count: Int) {
        badge.isVisible = count > 0
        badge.number = count
    }
}