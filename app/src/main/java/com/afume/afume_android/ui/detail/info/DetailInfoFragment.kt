package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.FragmentDetailInfoBinding
import com.afume.afume_android.ui.filter.FlexboxRecyclerViewAdapter
import com.afume.afume_android.ui.filter.RvFlexboxData
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class DetailInfoFragment : Fragment() {

    lateinit var binding: FragmentDetailInfoBinding
    lateinit var rvKeywordAdapter: FlexboxRecyclerViewAdapter
    private lateinit var rvSimilarAdapter: PopularListAdapter
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
        initLastingPowerBarChart()
        initsillageBarChart()
        initRvSimilar(requireContext())

        drawGenderPieChart(dummyPieDataGender())

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

    private fun drawGenderPieChart(pieDataSet: PieDataSet){
        val pieDataColors= listOf<Int>(
            ContextCompat.getColor(requireContext(),R.color.point_beige_accent),
            ContextCompat.getColor(requireContext(),R.color.point_beige),
            ContextCompat.getColor(requireContext(),R.color.light_beige)
        )
        val pieValuesTextColors= listOf<Int>(
            ContextCompat.getColor(requireContext(),R.color.white),
            ContextCompat.getColor(requireContext(),R.color.white),
            ContextCompat.getColor(requireContext(),R.color.dark_gray_7d),
        )
        pieDataSet.apply {
            colors=pieDataColors
            valueTextSize=14f
            setDrawValues(true)
            sliceSpace=1f
            setValueTextColors(pieValuesTextColors)
        }

        val pieData=PieData(pieDataSet)

        binding.chartPieDetailsInfoGender.apply {
//            setEntryLabelColor(ContextCompat.getColor(requireContext(),R.color.dark_gray_7d))
            setDrawEntryLabels(false)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                setLeftTopRightBottom(0,5,10,5)
//            }
            setUsePercentValues(false)
            offsetLeftAndRight(1)
            minOffset=0f
            isDrawHoleEnabled=false
            data = pieData
            highlightValue(0f,0)
            description.isEnabled = false
            invalidate()
        }

        val pieLegend = binding.chartPieDetailsInfoGender.legend
        pieLegend.apply {
//            yOffset=10f
//            xOffset=20f
//            xEntrySpace=20f

            form=Legend.LegendForm.SQUARE
            formSize=15f
            direction=Legend.LegendDirection.LEFT_TO_RIGHT
            verticalAlignment=Legend.LegendVerticalAlignment.CENTER
            horizontalAlignment=Legend.LegendHorizontalAlignment.RIGHT
            orientation=Legend.LegendOrientation.VERTICAL
//            maxSizePercent=0.8f
            textSize=16f
            textColor=ContextCompat.getColor(requireContext(),R.color.primary_black)
        }
    }
    private fun dummyPieDataGender():PieDataSet{
        val pieListData = listOf<PieEntry>(
            PieEntry(60f,"여성   60%"),
            PieEntry(40f,"남성   40%"),
            PieEntry(20f,"중성   20%"),
        )

        return PieDataSet(pieListData,"")
    }

}