package com.scentsnote.android.ui.survey

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentSurveyKeywordBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.viewmodel.survey.SurveyViewModel
import com.scentsnote.android.utils.adapter.FlexboxRecyclerViewAdapter

class SurveyKeywordFragment : Fragment() {
    private lateinit var binding: FragmentSurveyKeywordBinding
    private lateinit var surveyKeywordAdapter: FlexboxRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvKeyword(context)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setActiveButton(1)
    }

    private fun initBinding(container: ViewGroup?): View {
        binding = FragmentSurveyKeywordBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.listEmpty.text = String.format(getString(R.string.txt_list_empty), "키워드")
        viewModel.setActiveButton(1)
        binding.vm = viewModel
        return binding.root
    }

    private fun initRvKeyword(ctx: Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }
        surveyKeywordAdapter =
            FlexboxRecyclerViewAdapter(
                select = { index, b -> viewModel.addKeywordList(index.keywordIdx, b) },
                isOverSelectLimit = { false }
            )
        binding.rvSurveyKeyword.apply {
            layoutManager = flexboxLayoutManager
            setEmptyView(binding.listEmpty)
            adapter = surveyKeywordAdapter
            itemAnimator = null
        }
    }

}