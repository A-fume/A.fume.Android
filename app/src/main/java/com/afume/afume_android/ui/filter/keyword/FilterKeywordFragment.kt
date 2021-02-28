package com.afume.afume_android.ui.filter.keyword

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
import com.afume.afume_android.databinding.FragmentFilterKeywordBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.survey.SurveyViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class FilterKeywordFragment : Fragment() {
    private lateinit var binding: FragmentFilterKeywordBinding
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterKeywordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initKeywordRv(context)

    }
    private fun initKeywordRv(ctx: Context?){
        val flexboxLayoutManager= FlexboxLayoutManager(ctx).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val keywordAdapter =
            FlexboxRecyclerViewAdapter(
                {index->viewModel.addKeywordList(index)},
                {index->viewModel.removeKeywordList(index)}
            )
        binding.rvKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

//        keywordAdapter?.data= listOf(
//            ResponseKeyword("#시트러스"),
//            ResponseKeyword("#머스크"),
//            ResponseKeyword("#알데히드"),
//            ResponseKeyword("#스파이시"),
//            ResponseKeyword("#우디"),
//            ResponseKeyword("#플로럴"),
//            ResponseKeyword("#바닐라"),
//            ResponseKeyword("#민트"),
//            ResponseKeyword("#애니멀"),
//        )

        val keywordSelectionTracker= SelectionTracker.Builder<Long>(
            "incense",
            binding.rvKeyword,
            ItemKeyProvider(binding.rvKeyword),
            ItemDetailsLookUp(
                binding.rvKeyword,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        keywordAdapter.setSelectionTracker(keywordSelectionTracker)
    }
}