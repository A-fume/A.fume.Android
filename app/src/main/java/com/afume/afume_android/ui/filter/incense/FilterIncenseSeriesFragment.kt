package com.afume.afume_android.ui.filter.incense

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
import com.afume.afume_android.databinding.FragmentFilterIncenseSeriesBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.survey.SurveyViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class FilterIncenseSeriesFragment : Fragment() {
    private lateinit var binding:FragmentFilterIncenseSeriesBinding
    private val viewModel: SurveyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFilterIncenseSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIncenseSeriesRv(context)
    }
    private fun initIncenseSeriesRv(ctx: Context?){
        val flexboxLayoutManager=FlexboxLayoutManager(ctx).apply {
            flexDirection=FlexDirection.ROW
            flexWrap=FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val incenseAdapter=
            FlexboxRecyclerViewAdapter(
                {index->viewModel.addKeywordList(index)},
                {index->viewModel.removeKeywordList(index)}
            )
        binding.rvIncenseSeries.apply {
            layoutManager=flexboxLayoutManager
            adapter=incenseAdapter
        }

        incenseAdapter?.data= listOf(
            ResponseKeyword("시트러스"),
            ResponseKeyword("머스크"),
            ResponseKeyword("알데히드"),
            ResponseKeyword("스파이시"),
            ResponseKeyword("우디"),
            ResponseKeyword("플로럴"),
            ResponseKeyword("바닐라"),
            ResponseKeyword("민트"),
            ResponseKeyword("애니멀"),
            )

        val incenseSelectionTracker= SelectionTracker.Builder<Long>(
            "incense",
            binding.rvIncenseSeries,
            ItemKeyProvider(binding.rvIncenseSeries),
            ItemDetailsLookUp(
                binding.rvIncenseSeries,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        incenseAdapter.setSelectionTracker(incenseSelectionTracker)
      }
}