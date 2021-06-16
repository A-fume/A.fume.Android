package com.afume.afume_android.ui.filter.brand

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.afume.afume_android.databinding.FragmentFilterBrandBinding
import com.afume.afume_android.ui.filter.FilterViewModel
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.google.android.material.tabs.TabLayout

class FilterBrandFragment : Fragment() {
    private val viewModel: FilterViewModel by activityViewModels()

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
        initBrandTab()
        setTabClickEvent()
        initBrandRvItem(context)
        observeBlockClickMoreThan5()


    }

    override fun onResume() {
        super.onResume()
        observeBlockClickMoreThan5()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFilterBrandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    private fun initBrandTab() {
        binding.tabBrand.apply {
            addTab(binding.tabBrand.newTab().setText("ㄱ"))
            addTab(binding.tabBrand.newTab().setText("ㄴ"))
            addTab(binding.tabBrand.newTab().setText("ㄷ"))
            addTab(binding.tabBrand.newTab().setText("ㄹ"))
            addTab(binding.tabBrand.newTab().setText("ㅁ"))
            addTab(binding.tabBrand.newTab().setText("ㅂ"))
            addTab(binding.tabBrand.newTab().setText("ㅅ"))
            addTab(binding.tabBrand.newTab().setText("ㅇ"))
            addTab(binding.tabBrand.newTab().setText("ㅈ"))
            addTab(binding.tabBrand.newTab().setText("ㅊ"))
            addTab(binding.tabBrand.newTab().setText("ㅋ"))
            addTab(binding.tabBrand.newTab().setText("ㅌ"))
            addTab(binding.tabBrand.newTab().setText("ㅍ"))
            addTab(binding.tabBrand.newTab().setText("ㅎ"))
        }


    }

    private fun initBrandRvItem(ctx: Context?) {

        brandAdapter = BrandRecyclerViewAdapter(
            { brandIdx, b -> viewModel.setSelectedBrandListIdx(brandIdx, b) },
            { idx, b -> viewModel.countBadges(idx, b) })

        binding.rvBrand.apply {
            adapter = brandAdapter
            layoutManager = LinearLayoutManager(ctx)
        }

        val brandSelectionTracker = SelectionTracker.Builder<Long>(
            "brandName",
            binding.rvBrand,
            ItemKeyProvider(binding.rvBrand),
            ItemDetailsLookUp(
                binding.rvBrand,
                "brand"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        binding.tabBrand.getTabAt(0)?.select()
    }

    private fun setTabClickEvent() {
        val tab = binding.tabBrand
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                selectInitial(tab)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectInitial(tab)
            }
        })
    }

    fun selectInitial(tab: TabLayout.Tab?){
        when (tab?.position) {
            0 -> bindInitialBrand("ㄱ")
            1 -> bindInitialBrand("ㄴ")
            2 -> bindInitialBrand("ㄷ")
            3 -> bindInitialBrand("ㄹ")
            4 -> bindInitialBrand("ㅁ")
            5 -> bindInitialBrand("ㅂ")
            6 -> bindInitialBrand("ㅅ")
            7 -> bindInitialBrand("ㅇ")
            8 -> bindInitialBrand("ㅈ")
            9 -> bindInitialBrand("ㅊ")
            10 -> bindInitialBrand("ㅋ")
            11 -> bindInitialBrand("ㅌ")
            12 -> bindInitialBrand("ㅍ")
            13 -> bindInitialBrand("ㅎ")
        }
    }

    fun bindInitialBrand(initial: String) {
        brandAdapter.initial = initial
        brandAdapter.data = viewModel.bindBrandTab(initial)
        brandAdapter.notifyDataSetChanged()
    }

    fun observeBlockClickMoreThan5(){
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
            viewModel.blockClickBrandMoreThan5()
        })
    }


}