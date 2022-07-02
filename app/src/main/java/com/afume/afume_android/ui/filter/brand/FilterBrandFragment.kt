package com.afume.afume_android.ui.filter.brand

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afume.afume_android.databinding.FragmentFilterBrandBinding
import com.afume.afume_android.ui.filter.FilterViewModel
import com.afume.afume_android.ui.filter.FilterViewModelFactory
import com.afume.afume_android.util.TabSelectedListener
import com.afume.afume_android.util.changeTabsFont
import com.google.android.material.tabs.TabLayout

class FilterBrandFragment : Fragment() {
    private val viewModel: FilterViewModel by activityViewModels() {
        FilterViewModelFactory.getInstance()
    }

    private lateinit var binding: FragmentFilterBrandBinding
    private lateinit var brandAdapter: BrandRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initBrandTab()
        observeTab()
        setTabClickEvent()
        initBrandRvItem(context)
        observeBlockClickMoreThan5()
    }

    override fun onResume() {
        super.onResume()
//        initBrandTab()
//        observeTab()
        observeBlockClickMoreThan5()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFilterBrandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root
    }

    private fun initBrandTab() {
        Log.d("viewModel tab 개수", viewModel.brandTabOrders.value?.size.toString())
        viewModel.brandTabOrders.value?.forEach { b ->
            Log.d("frag brand", b)

            binding.tabBrand.addTab(binding.tabBrand.newTab().setText(b))
        }
        Log.d("frag getBrand", viewModel.brandMap.value.toString())
        Log.d("frag getBrand", viewModel.brandTabOrders.value.toString())
        binding.tabBrand.changeTabsFont(0)
    }

    private fun initBrandRvItem(ctx: Context?) {

        brandAdapter = BrandRecyclerViewAdapter(
            { brandIdx, b -> viewModel.setSelectedBrandListIdx(brandIdx, b) },
            { idx, b -> viewModel.countBadges(idx, b) })

        binding.rvBrand.apply {
            adapter = brandAdapter
            layoutManager = LinearLayoutManager(ctx)
        }

//        binding.tabBrand.getTabAt(0)?.select()
    }

    private fun setTabClickEvent() {
        val tabBrand = binding.tabBrand
        tabBrand.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                selectInitial(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectInitial(tab)
                tab?.position?.let{
                    tabBrand.changeTabsFont(it)
                }
            }
        })
    }

    fun selectInitial(tab: TabLayout.Tab?) {
        val initial = tab?.position?.let { viewModel.brandTabOrders.value?.get(it) }
        if (initial != null) bindInitialBrand(initial)
    }

    fun bindInitialBrand(initial: String) {
        brandAdapter.initial = initial
        brandAdapter.data = viewModel.bindBrandTab(initial)
        brandAdapter.notifyDataSetChanged()
    }

    fun observeBlockClickMoreThan5() {
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
            viewModel.blockClickBrandMoreThan5()
        })
    }

    fun observeTab() {
        viewModel.brandTabOrders.observe(viewLifecycleOwner, Observer {
            initBrandTab()
        })
    }
}