package com.scentsnote.android.ui.filter.brand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scentsnote.android.databinding.FragmentFilterBrandBinding
import com.scentsnote.android.utils.base.BaseWebViewActivity
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.data.vo.response.BrandTab
import com.scentsnote.android.utils.etc.Log
import com.scentsnote.android.utils.extension.setPageViewEvent

/**
 * 향수 검색 - 필터 - 브랜드 탭
 *
 * ㄱ,ㄴ,ㄷ 초성 리스트와 하위 브랜드 리스트 제공
 */
class FilterBrandFragment : Fragment() {
    private val viewModel: FilterBrandViewModel by activityViewModels()

    private lateinit var binding: FragmentFilterBrandBinding
    private lateinit var brandAdapter: BrandRecyclerViewAdapter
    private lateinit var brandTabAdapter : BrandTabRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTab()
        initBrandRvItem(context)
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("FilterBrand", this::class.java.name)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFilterBrandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.fm = this

        return binding.root
    }

    private fun observeTab() {
        viewModel.brandTabList.observe(viewLifecycleOwner) {
            initBrandTab(context, it)
        }
    }

    private fun initBrandTab(ctx: Context?, brandTab: MutableList<BrandTab>) {
        brandTabAdapter = BrandTabRecyclerViewAdapter(brandTab, viewModel)
        binding.rvFilterBrandTab.apply {
            adapter = brandTabAdapter
            layoutManager = LinearLayoutManager(ctx)
        }
        updateContents(0)
        brandTabAdapter.itemClickListener = object : BrandTabRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                updateContents(position)
            }
        }
    }

    private fun initBrandRvItem(ctx: Context?) {
        brandAdapter = BrandRecyclerViewAdapter(viewModel)
        binding.rvBrand.apply {
            adapter = brandAdapter
            layoutManager = LinearLayoutManager(ctx)
        }
    }

    fun updateContents(tabIndex: Int) {
        val brandInitial = viewModel.brandTabOrders.value?.get(tabIndex)
        if (brandInitial != null) bindBrandInitial(brandInitial)
    }

    private fun bindBrandInitial(initial: String) {
        brandAdapter.initial = initial
        brandAdapter.submitList(viewModel.bindBrandTab(initial))
    }

    fun onClickWithdrawalBtn(view: View) {
        val intent = Intent(requireContext(), BaseWebViewActivity::class.java)
        intent.putExtra("url", "tipOff")
        startActivity(intent)
    }

    fun resetBrandList(){
        viewModel.resetSelectedBrandList()
        brandAdapter.notifyDataSetChanged()
    }
}