package com.scentsnote.android.ui.filter

import android.annotation.SuppressLint
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
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.FragmentFilterBinding
import com.scentsnote.android.ui.filter.brand.FilterBrandFragment
import com.scentsnote.android.ui.filter.incense.FilterIncenseSeriesFragment
import com.scentsnote.android.ui.filter.keyword.FilterKeywordFragment
import com.scentsnote.android.utils.etc.Log
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

        binding.tabFilter.changeTabsFont(0)
        firebaseAnalytics.setPageViewEvent("Filter", this::class.java.name)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.btnFilterApply.setOnSafeClickListener {
            sendFilter()
            brandViewModel.setBrandTab()

            firebaseAnalytics.setClickEvent("FilterActionButton")

            reqFilterGa("apply_filter", seriesViewModel.getSelectedSeries())
            reqFilterGa("apply_brand", brandViewModel.getSelectedBrands())
            reqFilterGa("apply_bonding", keywordViewModel.getSelectedKeywords())

            Log.d("GA 필터 - 계열", seriesViewModel.getSelectedSeries().map { it.name }.toString())
            Log.d("GA 필터 - 브랜드", brandViewModel.getSelectedBrands().map { it.name }.toString())
            Log.d("GA 필터 - 키워드", keywordViewModel.getSelectedKeywords().map { it.name }.toString())
        }
        binding.toolbarFilter.toolbarBtn.setOnSafeClickListener {
            closeSelfWithAnimation()
            brandViewModel.setBrandTab()

            firebaseAnalytics.setClickEvent("FilterPauseButton")
        }

        binding.toolbarFilter.apply {
            toolbar = R.drawable.icon_btn_cancel
            toolbartxt = "필터"
        }

        binding.btnFilterRefresh.setOnSafeClickListener {
            binding.btnFilterApply.apply {
                text = "적용"
                isEnabled = false
            }
            resetFilter()
        }
    }

    private fun reqFilterGa(type: String, list : List<FilterInfoP>){
        list.forEach {
            firebaseAnalytics.setOneParamClickEvent("kind_of_filter", type, it.name)
        }
    }

    private fun resetFilter(){
        val seriesFragment = filterViewPagerAdapter.getItem(0) as FilterIncenseSeriesFragment
        seriesFragment.resetSeriesList()
        val brandFragment = filterViewPagerAdapter.getItem(1) as FilterBrandFragment
        brandFragment.resetBrandList()
        val keywordFragment = filterViewPagerAdapter.getItem(2) as FilterKeywordFragment
        keywordFragment.resetKeywordList()
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
            }
            addOnTabSelectedListener(TabSelectedListener(binding.tabFilter))
            changeTabsFont(0)
        }
    }

    private fun observeViewModel() {
        seriesViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Series.index)
            if(count != 0){
                tab?.text = FilterCategory.Series.nameText+"($count)"
            }else{
                tab?.text = FilterCategory.Series.nameText
            }
            updateApplyBtnText()
            binding.tabFilter.changeTabsFont(FilterCategory.Series.index)
        }

        brandViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Brand.index)
            if(count != 0){
                tab?.text = FilterCategory.Brand.nameText+"($count)"
            }else{
                tab?.text = FilterCategory.Brand.nameText
            }
            updateApplyBtnText()
            binding.tabFilter.changeTabsFont(FilterCategory.Brand.index)
        }

        keywordViewModel.selectedCount.observe(viewLifecycleOwner) { count ->
            val tab = binding.tabFilter.getTabAt(FilterCategory.Keyword.index)
            if(count != 0){
               tab?.text = FilterCategory.Keyword.nameText+"($count)"
            }else{
                tab?.text = FilterCategory.Keyword.nameText
            }
            updateApplyBtnText()
            binding.tabFilter.changeTabsFont(FilterCategory.Keyword.index)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateApplyBtnText() {
        val totalCount = seriesViewModel.count + brandViewModel.count + keywordViewModel.count
        binding.btnFilterApply.apply {
            if (totalCount == 0) {
                text = "적용"
                isEnabled = false
            } else {
                text = "적용($totalCount)"
                isEnabled = true
            }
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
        val filterInfoNameList = seriesViewModel.getSelectedSeriesName()+
                brandViewModel.getSelectedBrandsName() +
                keywordViewModel.getSelectedKeywordsName()
        firebaseAnalytics.setOneParamClickEvent("kind_of_filter", "kind_of_filter_name", filterInfoNameList)
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