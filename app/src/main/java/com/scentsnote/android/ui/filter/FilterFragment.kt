package com.scentsnote.android.ui.filter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.badge.BadgeDrawable
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.FragmentFilterBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.brand.FilterBrandFragment
import com.scentsnote.android.ui.filter.incense.FilterIncenseSeriesFragment
import com.scentsnote.android.ui.filter.keyword.FilterKeywordFragment
import com.scentsnote.android.utils.extension.changeTabsFont
import com.scentsnote.android.utils.extension.closeSelf
import com.scentsnote.android.utils.extension.closeSelfWithAnimation
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.listener.TabSelectedListener
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel


class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var filterViewPagerAdapter: ScentsNoteViewPagerAdapter
    private val seriesViewModel: FilterSeriesViewModel by activityViewModels()
    private val brandViewModel: FilterBrandViewModel by activityViewModels()
    private val keywordViewModel: FilterKeywordViewModel by activityViewModels()
    private val filterCategoryList = FilterCategory.values()

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
            closeSelf()
        }
        binding.toolbarFilter.toolbarBtn.setOnSafeClickListener {
            closeSelfWithAnimation()
        }

        binding.toolbarFilter.toolbar = R.drawable.icon_btn_cancel
        binding.toolbarFilter.toolbartxt = "필터"
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
        val intent = Intent(requireContext(), MainActivity::class.java)
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

    private fun updateCategoryBadge(badge: BadgeDrawable, count: Int) {
        badge.isVisible = count > 0
        badge.number = count
    }

    companion object {
        fun newInstance(): FilterFragment = FilterFragment()
    }
}