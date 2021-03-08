package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.databinding.FragmentDetailInfoBinding
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.afume.afume_android.util.FlexboxRecyclerViewAdapter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
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
        drawBubbleChartSeason(dummyBubbleDataSeason())

    }

    private fun initRvKeyword(ctx:Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        rvKeywordAdapter =
            FlexboxRecyclerViewAdapter(
                { index -> print(index) },
                { index -> print(index) }
            )
        binding.rvDetailsInfoKeyword.apply {
            adapter = rvKeywordAdapter
            layoutManager = flexboxLayoutManager
        }

        rvKeywordAdapter.data = mutableListOf(
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
        rvKeywordAdapter.notifyDataSetChanged()
        val keywordSelectionTracker= SelectionTracker.Builder<Long>(
            "survey_keyword",
            binding.rvDetailsInfoKeyword,
            ItemKeyProvider(binding.rvDetailsInfoKeyword),
            ItemDetailsLookUp(
                binding.rvDetailsInfoKeyword,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        rvKeywordAdapter.setSelectionTracker(keywordSelectionTracker)
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

    private fun drawBubbleChartSeason(dataSet: List<BubbleDataSet>){

        val yR=binding.chartBubbleDetailsInfoSeason.axisRight
        yR.apply {
            setDrawZeroLine(false)
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }
        val yL=binding.chartBubbleDetailsInfoSeason.axisLeft
        yL.apply {
            setDrawZeroLine(false)
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
            axisMaximum = 65f
            axisMinimum = 15f
        }
        val xl = binding.chartBubbleDetailsInfoSeason.xAxis
        xl.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
            granularity=1f
            axisMaximum = 4.5f
            axisMinimum = 0.5f
        }

        val bubbleData = BubbleData(dataSet)
        bubbleData.apply {
            setValueTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            setValueTextSize(15f)
        }

        binding.chartBubbleDetailsInfoSeason.apply {
            description.isEnabled=false
            legend.isEnabled=false
            setDrawGridBackground(false)
            setTouchEnabled(false)
            setScaleEnabled(false)
            isDragXEnabled=false
            isDragYEnabled=false
            axisRight.isEnabled=false
            data=bubbleData
            invalidate()
        }

    }

    private fun dummyBubbleDataSeason():List<BubbleDataSet>{
        val primarySeasonDataSet=BubbleDataSet(listOf(BubbleEntry(1f,50f,50f)),"primary")
        primarySeasonDataSet.color = ContextCompat.getColor(requireContext(),R.color.point_beige_accent)

        val seasonDataEntryList= listOf<BubbleEntry>(
            BubbleEntry(2f,30f,30f),
            BubbleEntry(3f,25f,25f),
            BubbleEntry(4f,25f,25f)
        )
        val seasonDataSet=BubbleDataSet(seasonDataEntryList,"normal")
        seasonDataSet.color = ContextCompat.getColor(requireContext(),R.color.point_beige)

        return listOf<BubbleDataSet>(
            primarySeasonDataSet,
            seasonDataSet
        )

    }


}