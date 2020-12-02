package com.afume.afume_android.filter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afume.afume_android.databinding.FragmentFilterKeywordBinding
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
        initKeywordRv(context!!)

    }
    private fun initKeywordRv(ctx: Context){
        val flexboxLayoutManager= FlexboxLayoutManager(ctx).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val keywordAdapter=FlexboxRecyclerViewAdapter(ctx)
        binding.rvKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

        keywordAdapter.data= listOf(
            IncenseSeriesViewModel("#시트러스"),IncenseSeriesViewModel("#머스크"),IncenseSeriesViewModel("#알데히드"),
            IncenseSeriesViewModel("#스파이시"),IncenseSeriesViewModel("#우디"),IncenseSeriesViewModel("#플로럴"),
            IncenseSeriesViewModel("#바닐라"),IncenseSeriesViewModel("#민트"),IncenseSeriesViewModel("#애니멀"),
        )
    }
}