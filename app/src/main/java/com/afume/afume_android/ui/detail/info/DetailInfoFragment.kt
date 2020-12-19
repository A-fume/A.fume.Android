package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.FragmentDetailInfoBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.RvFlexboxData
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class DetailInfoFragment : Fragment() {

    lateinit var binding: FragmentDetailInfoBinding
    lateinit var rvKeywordAdapter: FlexboxRecyclerViewAdapter
    lateinit var rvSimilarAdapter: PopularListAdapter
    lateinit var chartLastingPowerAdapter: HorizontalBarChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvKeyword(context)
        initRvSimilar(requireContext())
        initLastingPowerBarChart()
        chartLastingPowerAdapter.notifyDataSetChanged()
        initsillageBarChart()

    }

    private fun initRvKeyword(ctx:Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        rvKeywordAdapter = FlexboxRecyclerViewAdapter(2)
        binding.rvDetailsInfoKeyword.apply {
            adapter = rvKeywordAdapter
            layoutManager = flexboxLayoutManager
        }

        rvKeywordAdapter.data = listOf(
            RvFlexboxData("#꽃향의"),
            RvFlexboxData("#여성스러운"),
            RvFlexboxData("#데일리"),
            RvFlexboxData("#여대생"),
            RvFlexboxData("#러블리한"),
            RvFlexboxData("#세련된"),
            RvFlexboxData("#봄같은"),
            RvFlexboxData("#첫사랑같은"),
            RvFlexboxData("#소녀스러운")
        )
        rvKeywordAdapter.notifyDataSetChanged()
    }
    private fun initRvSimilar(ctx: Context){
        rvSimilarAdapter= PopularListAdapter(ctx)
        binding.rvDetailsInfoSimilar.adapter=rvSimilarAdapter
        rvSimilarAdapter.data=mutableListOf(
            HomePerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                like = 0
            ),
            HomePerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                like = 0
            )
        )
        rvSimilarAdapter.notifyDataSetChanged()
    }

    private fun initLastingPowerBarChart(){
        chartLastingPowerAdapter=HorizontalBarChartAdapter(0,context)
        binding.chartBarDetailsInfoLastingPower.adapter=chartLastingPowerAdapter
        chartLastingPowerAdapter.chartData= listOf(50f,30f,10f,5f,5f)
        chartLastingPowerAdapter.notifyDataSetChanged()
    }

    private fun initsillageBarChart(){
        val sillageAdapter=HorizontalBarChartAdapter(1,context)
        binding.chartBarDetailsInfoSillage.adapter=sillageAdapter
        sillageAdapter.chartData= listOf(60f,30f,10f)
        sillageAdapter.notifyDataSetChanged()
    }

}