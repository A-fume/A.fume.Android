package com.scentsnote.android.ui.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.badge.BadgeDrawable
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.FragmentFilterBinding
import com.scentsnote.android.ui.filter.brand.FilterBrandFragment
import com.scentsnote.android.ui.filter.incense.FilterIncenseSeriesFragment
import com.scentsnote.android.ui.filter.keyword.FilterKeywordFragment
import com.scentsnote.android.utils.extension.*
import com.scentsnote.android.utils.extension.closeSelfWithAnimation
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.utils.listener.TabSelectedListener
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel
import com.scentsnote.android.viewmodel.search.SearchViewModel


class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var filterViewPagerAdapter: ScentsNoteViewPagerAdapter
    private val seriesViewModel: FilterSeriesViewModel by activityViewModels()
    private val brandViewModel: FilterBrandViewModel by activityViewModels()
    private val keywordViewModel: FilterKeywordViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val filterCategoryList = FilterCategory.values()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        setUpTabWithViewPager()

        observeViewModel()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.btnFilterApply.setOnSafeClickListener {
            sendFilter()

            firebaseAnalytics.setClickEvent("FilterActionButton")
        }
        binding.toolbarFilter.toolbarBtn.setOnSafeClickListener {
            closeSelfWithAnimation()

            firebaseAnalytics.setClickEvent("FilterPauseButton")
        }

        binding.toolbarFilter.toolbar = R.drawable.icon_btn_cancel
        binding.toolbarFilter.toolbartxt = "필터"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                closeSelfWithAnimation()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("Filter", "FilterFragment")
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    private fun initViewPager() {
        filterViewPagerAdapter = ScentsNoteViewPagerAdapter(parentFragmentManager)
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
        seriesViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Series.index)
            tab?.orCreateBadge?.let {
                updateCategoryBadge(it, count)
            }
            updateApplyBtnText()
        }

        brandViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Brand.index)
            tab?.orCreateBadge?.let {
                updateCategoryBadge(it, count)
            }
            updateApplyBtnText()
        }

        keywordViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
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
        searchViewModel.sendFilter(getSelectedFilters())
        closeSelfWithAnimation()
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

    private fun updateCategoryBadge(badge: BadgeDrawable, count: Int) {
        badge.isVisible = count > 0
        badge.number = count
    }

    companion object {
        fun newInstance(): FilterFragment = FilterFragment()
    }
}