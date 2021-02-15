package com.afume.afume_android.ui.survey

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.databinding.FragmentSurveyKeywordBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SurveyKeywordFragment : Fragment() {
    private lateinit var binding:FragmentSurveyKeywordBinding
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentSurveyKeywordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvKeyword(context)
    }

    private fun initRvKeyword(ctx:Context?){
        val flexboxLayoutManager= FlexboxLayoutManager(ctx).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }
        val keywordAdapter = FlexboxRecyclerViewAdapter(
            {index->viewModel.addKeywordList(index)},
            {index->viewModel.removeKeywordList(index)}
        )
        binding.rvSurveyKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

        keywordAdapter?.data= listOf(
            ResponseKeyword("#산뜻한"),
            ResponseKeyword("#자연의"),
            ResponseKeyword("#여성스러운"),
            ResponseKeyword("#비누향"),
            ResponseKeyword("#남성적인"),
            ResponseKeyword("#몽환적인"),
            ResponseKeyword("#소녀스러운"),
            ResponseKeyword("#달달한"),
            ResponseKeyword("#매운"),
            ResponseKeyword("#상쾌한"),
            ResponseKeyword("#도시적인"),
            ResponseKeyword("#톡 쏘는"),
            ResponseKeyword("#자연의"),
            ResponseKeyword("#여성스러운"),
            ResponseKeyword("#비누향")
        )

        val keywordSelectionTracker= SelectionTracker.Builder<Long>(
            "survey_keyword",
            binding.rvSurveyKeyword,
            ItemKeyProvider(binding.rvSurveyKeyword),
            ItemDetailsLookUp(
                binding.rvSurveyKeyword,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        keywordAdapter.setSelectionTracker(keywordSelectionTracker)

        keywordAdapter.notifyDataSetChanged()
    }

}