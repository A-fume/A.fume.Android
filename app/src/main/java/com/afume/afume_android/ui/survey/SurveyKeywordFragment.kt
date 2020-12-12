package com.afume.afume_android.ui.survey

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentSurveyKeywordBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.filter.RvFlexboxData
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SurveyKeywordFragment : Fragment() {
    private lateinit var binding:FragmentSurveyKeywordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val keywordAdapter = FlexboxRecyclerViewAdapter()
        binding.rvSurveyKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }
        keywordAdapter?.data= listOf(
            RvFlexboxData("#산뜻한"),
            RvFlexboxData("#자연의"),
            RvFlexboxData("#여성스러운"),
            RvFlexboxData("#비누향"),
            RvFlexboxData("#남성적인"),
            RvFlexboxData("#몽환적인"),
            RvFlexboxData("#소녀스러운"),
            RvFlexboxData("#달달한"),
            RvFlexboxData("#매운"),
            RvFlexboxData("#상쾌한"),
            RvFlexboxData("#도시적인"),
            RvFlexboxData("#톡 쏘는"),
            RvFlexboxData("#자연의"),
            RvFlexboxData("#여성스러운"),
            RvFlexboxData("#비누향")
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