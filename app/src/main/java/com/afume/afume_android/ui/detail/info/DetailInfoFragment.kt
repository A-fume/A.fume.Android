package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentDetailInfoBinding
import com.afume.afume_android.ui.detail.PerfumeDetailViewModel
import com.afume.afume_android.ui.home.HomeViewModel
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class DetailInfoFragment(val perfumeIdx: Int) : Fragment() {

    lateinit var binding: FragmentDetailInfoBinding
    lateinit var rvKeywordAdapter: DetailKeywordAdapter
    private val rvPriceAdapter = PriceRvAdapter()
    private lateinit var rvSimilarAdapter: PopularListAdapter
    lateinit var chartLastingPowerAdapter: HorizontalBarChartAdapter
    private val viewModel: PerfumeDetailViewModel by activityViewModels()
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPerfumeInfo(perfumeIdx)
        observe()

        initRv(context)
        initLastingPowerBarChart(0,0,0,0,0)
        initsillageBarChart(0,0,0)
        initRvSimilar(requireContext())

        drawGenderPieChart(dummyPieDataGender(0f, 0f, 0f))
        drawBubbleChartSeason(dummyBubbleDataSeason(0,0,0,0))

    }

    private fun initRv(ctx: Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }
        rvKeywordAdapter = DetailKeywordAdapter()
            .apply {
                setHasStableIds(true)
            }

        binding.rvDetailsInfoKeyword.run {
            adapter = rvKeywordAdapter
            layoutManager = flexboxLayoutManager
        }

        binding.fragDetailRvPrice.run { adapter = rvPriceAdapter }
    }

    private fun initRvSimilar(ctx: Context) {
        rvSimilarAdapter = PopularListAdapter(parentFragmentManager){ idx -> homeViewModel.postPerfumeLike(0,idx)}
        binding.rvDetailsInfoSimilar.adapter = rvSimilarAdapter
//        rvSimilarAdapter.data = mutableListOf(
//            HomePerfumeListData(
//                image = null,
//                brand = "1번 브랜드",
//                name = "1번향수",
//                like = 0
//            ),
//            HomePerfumeListData(
//                image = null,
//                brand = "2번 브랜드",
//                name = "2번향수",
//                like = 1
//            ),
//            HomePerfumeListData(
//                image = null,
//                brand = "3번 브랜드",
//                name = "3번향수",
//                like = 1
//            ),
//            HomePerfumeListData(
//                image = null,
//                brand = "4번 브랜드",
//                name = "4번향수",
//                like = 0
//            )
//        )
        rvSimilarAdapter.notifyDataSetChanged()
    }

    private fun initLastingPowerBarChart(veryWeak: Int, weak: Int, medium: Int, strong: Int, veryStrong: Int) {
        chartLastingPowerAdapter = HorizontalBarChartAdapter(0, context)
        binding.chartBarDetailsInfoLastingPower.adapter = chartLastingPowerAdapter
        chartLastingPowerAdapter.chartData = listOf(veryWeak.toFloat(), weak.toFloat(), medium.toFloat(), strong.toFloat(), veryStrong.toFloat())
        chartLastingPowerAdapter.notifyDataSetChanged()
    }

    private fun initsillageBarChart(light: Int, medium: Int, heavy: Int) {
        val sillageAdapter = HorizontalBarChartAdapter(1, context)
        binding.chartBarDetailsInfoSillage.adapter = sillageAdapter
        sillageAdapter.chartData = listOf(light.toFloat(), medium.toFloat(), heavy.toFloat())
        sillageAdapter.notifyDataSetChanged()
    }

    private fun drawGenderPieChart(pieDataSet: PieDataSet) {
        val pieDataColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.point_beige_accent),
            ContextCompat.getColor(requireContext(), R.color.point_beige),
            ContextCompat.getColor(requireContext(), R.color.dark_beige)
        )
        val pieValuesTextColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
        )
        pieDataSet.apply {
            colors = pieDataColors
            valueTextSize = 14f
            setDrawValues(true)
            sliceSpace = 1f

            setValueTextColors(pieValuesTextColors)
//            valueFormatter = object: ValueFormatter(){
//                override fun getFormattedValue(value: PieEntry): String {
//                    return value.toInt().toString() + "개"
//                }
//            }
        }

        val pieData = PieData(pieDataSet)

        binding.chartPieDetailsInfoGender.apply {
//            setEntryLabelColor(ContextCompat.getColor(requireContext(),R.color.dark_gray_7d))
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                setLeftTopRightBottom(0,5,10,5)
//            }

            setDrawCenterText(false)
            setDrawEntryLabels(true) // 성별 표기
//            setDrawMarkers(true)
            setTouchEnabled(false) // 그래프 터치 (확대, 회전 등)
            setUsePercentValues(true) // 그래프 안에 텍스트를 퍼센트로 표기할건지 여부
            description.isEnabled = false // 그래프 설명(이름?)
            isDrawHoleEnabled = false // 가운데를 뚫을건지 여부

            minOffset = 0f
            data = pieData
            invalidate()
        }

        val pieLegend = binding.chartPieDetailsInfoGender.legend
        pieLegend.apply {
//            yOffset=10f
//            xOffset=20f
//            xEntrySpace=20f

            form = Legend.LegendForm.SQUARE
            formSize = 15f
            direction = Legend.LegendDirection.LEFT_TO_RIGHT
            verticalAlignment = Legend.LegendVerticalAlignment.CENTER
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
//            maxSizePercent=0.8f
            textSize = 16f
            textColor = ContextCompat.getColor(requireContext(), R.color.primary_black)
        }
    }

    private fun dummyPieDataGender(female: Float, male: Float, middle: Float): PieDataSet {
//        val pieListData = listOf<PieEntry>(
//            PieEntry(60f, "여성   60%"),
//            PieEntry(40f, "남성   40%"),
//            PieEntry(20f, "중성   20%"),
//        )

        val pieListData = listOf(
            PieEntry(female, "여성"),
            PieEntry(male, "남성"),
            PieEntry(middle, "중성"),
        )

        return PieDataSet(pieListData, "")
    }

    private fun drawBubbleChartSeason(dataSet: List<BubbleDataSet>) {

        val yR = binding.chartBubbleDetailsInfoSeason.axisRight
        yR.apply {
            setDrawZeroLine(false)
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }
        val yL = binding.chartBubbleDetailsInfoSeason.axisLeft
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
            granularity = 1f
            axisMaximum = 4.5f
            axisMinimum = 0.5f
        }

        val bubbleData = BubbleData(dataSet)
        bubbleData.apply {
            setValueTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setValueTextSize(15f)
            setValueFormatter(IndexAxisValueFormatter(listOf<String>("봄", "여름", "가을", "겨울")))
        }

        binding.chartBubbleDetailsInfoSeason.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setDrawGridBackground(false)
            setTouchEnabled(false)
            setScaleEnabled(false)
            isDragXEnabled = false
            isDragYEnabled = false
            axisRight.isEnabled = false
            data = bubbleData
            invalidate()
        }

    }

    private fun dummyBubbleDataSeason(sp: Int, sm: Int, fa: Int, wi: Int): List<BubbleDataSet> {
        val primarySeasonDataSet = BubbleDataSet(listOf(BubbleEntry(1f, 40f, sp.toFloat())), "primary")
        primarySeasonDataSet.color =
            ContextCompat.getColor(requireContext(), R.color.point_beige_accent)

        val seasonDataEntryList = listOf<BubbleEntry>(
            BubbleEntry(2f, 40f, sm.toFloat()),
            BubbleEntry(3f, 40f, fa.toFloat()),
            BubbleEntry(4f, 40f, wi.toFloat())
        )
        val seasonDataSet = BubbleDataSet(seasonDataEntryList, "normal")
        seasonDataSet.color = ContextCompat.getColor(requireContext(), R.color.point_beige)

        val set1 = BubbleDataSet(listOf(BubbleEntry(1f, 40f, sp.toFloat())), "봄")
        set1.setDrawValues(true)

        val set2 = BubbleDataSet(listOf(BubbleEntry(1f, 40f, sm.toFloat())), "여름")
        set2.setDrawValues(true)

        val set3 = BubbleDataSet(listOf(BubbleEntry(1f, 40f, fa.toFloat())), "가을")
        set3.setDrawValues(true)

        val set4 = BubbleDataSet(listOf(BubbleEntry(1f, 40f, wi.toFloat())), "겨울")
        set3.setDrawValues(true)

        val dataSets = ArrayList<IBubbleDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)
        dataSets.add(set4)
        var data = BubbleData(dataSets)
        data.setDrawValues(true)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)
        data.setHighlightCircleWidth(1.5f)
//        return data

        return listOf<BubbleDataSet>(
            primarySeasonDataSet,
            seasonDataSet
        )
    }


    private fun observe(){
        viewModel.perfumeDetailData.observe(requireActivity(), Observer {
            binding.run {
                data = it
//                txtDetailsInfoPrice1.text = it.volumeAndPrice[0]
//                txtDetailsInfoPrice2.text = it.volumeAndPrice[1]
                initLastingPowerBarChart(it.longevity.veryWeak, it.longevity.weak, it.longevity.medium, it.longevity.strong, it.longevity.veryStrong)
                initsillageBarChart(it.sillage.light, it.sillage.medium, it.sillage.heavy)
//                drawGenderPieChart(dummyPieDataGender(it.gender.female.toFloat(), it.gender.male.toFloat(), it.gender.neutral.toFloat()))
//                drawBubbleChartSeason(dummyBubbleDataSeason(it.seasonal.spring,it.seasonal.summer,it.seasonal.fall,it.seasonal.winter))
                drawGenderPieChart(dummyPieDataGender(50f, 30f, 20f))
                drawBubbleChartSeason(dummyBubbleDataSeason(0,30,30,0))
            }

            rvKeywordAdapter.run {
                replaceAll(ArrayList(it.Keywords))
                notifyDataSetChanged()
            }
            rvPriceAdapter.run {
                replaceAll(ArrayList(it.volumeAndPrice))
                notifyDataSetChanged()
            }
        })
    }
}