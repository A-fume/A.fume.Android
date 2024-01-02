package com.scentsnote.android.ui.filter.keyword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.databinding.FragmentFilterKeywordBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.utils.adapter.FlexboxRecyclerViewAdapter
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel

/**
 * 향수 검색 - 필터 - 키워드 탭
 *
 * 키워드 리스트 제공
 */
class FilterKeywordFragment : Fragment() {
    private val viewModel: FilterKeywordViewModel by activityViewModels()

    private lateinit var binding: FragmentFilterKeywordBinding
    private lateinit var keywordAdapter: FlexboxRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKeywordRv(context)
        observeBlockClickMoreThan5()
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("FilterKeyword", this::class.java.name)

        initKeywordRv(context)
        observeBlockClickMoreThan5()
        observeViewModel()
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

        keywordAdapter = FlexboxRecyclerViewAdapter(
            select = { info, selected -> viewModel.selectKeywordList(info, selected) },
            isOverSelectLimit = { viewModel.isOverSelectLimit() }
        )

        binding.rvKeyword.apply {
            layoutManager = flexboxLayoutManager
            adapter = keywordAdapter
            itemAnimator = null
        }
    }

    private fun observeViewModel() {
        viewModel.keywordList.observe(viewLifecycleOwner) {
            (binding.rvKeyword.adapter as FlexboxRecyclerViewAdapter).apply {
                submitList(it)
            }
        }
    }

    fun observeBlockClickMoreThan5() {
        // TODO remove
    }

    fun resetKeywordList(){
        keywordAdapter.notifyDataSetChanged()
    }

}