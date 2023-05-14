package com.scentsnote.android.ui.filter.keyword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.scentsnote.android.databinding.FragmentFilterKeywordBinding
import com.scentsnote.android.viewmodel.filter.FilterViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.utils.adapter.FlexboxRecyclerViewAdapter

/**
 * 향수 검색 - 필터 - 키워드 탭
 *
 * 키워드 리스트 제공
 */
class FilterKeywordFragment : Fragment() {
    private lateinit var binding: FragmentFilterKeywordBinding
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKeywordRv(context)
        observeBlockClickMoreThan5()
    }

    override fun onResume() {
        super.onResume()
        initKeywordRv(context)
        observeBlockClickMoreThan5()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFilterKeywordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    private fun initKeywordRv(ctx: Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val keywordAdapter = FlexboxRecyclerViewAdapter(
            { index, add -> viewModel.addKeywordList(index, add) },
            { index, add -> viewModel.countBadges(index, add) }
        )

        binding.rvKeyword.apply {
            layoutManager = flexboxLayoutManager
            adapter = keywordAdapter
        }
    }

    fun observeBlockClickMoreThan5() {
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
            viewModel.blockClickKeywordMoreThan5()
        })
    }

}