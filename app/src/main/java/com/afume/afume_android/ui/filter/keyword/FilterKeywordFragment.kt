package com.afume.afume_android.ui.filter.keyword

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.databinding.FragmentFilterKeywordBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.filter.RvFlexboxData
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class FilterKeywordFragment : Fragment() {
    private lateinit var binding: FragmentFilterKeywordBinding
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
            FlexboxRecyclerViewAdapter()
        binding.rvKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

        keywordAdapter?.data= listOf(
            RvFlexboxData("#시트러스"),
            RvFlexboxData("#머스크"),
            RvFlexboxData("#알데히드"),
            RvFlexboxData("#스파이시"),
            RvFlexboxData("#우디"),
            RvFlexboxData("#플로럴"),
            RvFlexboxData("#바닐라"),
            RvFlexboxData("#민트"),
            RvFlexboxData("#애니멀"),
        )
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