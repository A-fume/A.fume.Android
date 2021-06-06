package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentDetailInfoBinding
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class DetailInfoFragment : Fragment() {

    lateinit var binding: FragmentDetailInfoBinding
    lateinit var rvKeywordAdapter: DetailKeywordAdapter
    private lateinit var rvSimilarAdapter: PopularListAdapter
    lateinit var chartLastingPowerAdapter: HorizontalBarChartAdapter
    private val viewModel: PerfumeDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPerfumeInfo(1)
        observe()

        initRvKeyword(context)
        initLastingPowerBarChart(0,0,0,0,0)
        initsillageBarChart(0,0,0)
        initRvSimilar(requireContext())

        drawGenderPieChart(dummyPieDataGender(0f, 0f, 0f))
        drawBubbleChartSeason(dummyBubbleDataSeason())

    }

    private fun initRvKeyword(ctx: Context?) {
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

//        val tempList = arrayListOf(
//            "#산뜻한","#자연의", "#여성스러운", "#비누향", "#남성적인", "#비누향")
//        rvKeywordAdapter.run {
//            replaceAll(tempList)
//            notifyDataSetChanged()
//        }

//        val keywordSelectionTracker = SelectionTracker.Builder<Long>(
//            "survey_keyword",
//            binding.rvDetailsInfoKeyword,
//            ItemKeyProvider(binding.rvDetailsInfoKeyword),
//            ItemDetailsLookUp(
//                binding.rvDetailsInfoKeyword,
//                "flexbox"
//            ),
//            StorageStrategy.createLongStorage()
//        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
//        rvKeywordAdapter.setSelectionTracker(keywordSelectionTracker)
    }

    private fun initRvSimilar(ctx: Context) {
        rvSimilarAdapter = PopularListAdapter(ctx)
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
            ContextCompat.getColor(requireContext(), R.color.light_beige)
        )
        val pieValuesTextColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.dark_gray_7d),
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

            setDrawCenterText(true)
            setDrawEntryLabels(true)
//            setDrawMarkers(true)
            setTouchEnabled(false) // 그래프 터치 (확대, 회전 등)
            setUsePercentValues(false) // 그래프 안에 텍스트를 퍼센트로 표기할건지 여부
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

    private fun dummyBubbleDataSeason(): List<BubbleDataSet> {
        val primarySeasonDataSet = BubbleDataSet(listOf(BubbleEntry(1f, 50f, 50f)), "primary")
        primarySeasonDataSet.color =
            ContextCompat.getColor(requireContext(), R.color.point_beige_accent)

        val seasonDataEntryList = listOf<BubbleEntry>(
            BubbleEntry(2f, 30f, 30f),
            BubbleEntry(3f, 25f, 25f),
            BubbleEntry(4f, 25f, 25f)
        )
        val seasonDataSet = BubbleDataSet(seasonDataEntryList, "normal")
        seasonDataSet.color = ContextCompat.getColor(requireContext(), R.color.point_beige)

        return listOf<BubbleDataSet>(
            primarySeasonDataSet,
            seasonDataSet
        )
    }


    private fun observe(){
        viewModel.perfumeDetailData.observe(requireActivity(), Observer {
            binding.run {
                data = it
                txtDetailsInfoPrice1.text = it.volumeAndPrice[0]
                txtDetailsInfoPrice2.text = it.volumeAndPrice[1]
                dummyPieDataGender(it.gender.female.toFloat(), it.gender.male.toFloat(), it.gender.neutral.toFloat())
                initLastingPowerBarChart(it.longevity.veryWeak, it.longevity.weak, it.longevity.medium, it.longevity.strong, it.longevity.veryStrong)
                initsillageBarChart(it.sillage.light, it.sillage.medium, it.sillage.heavy)
            }

            rvKeywordAdapter.run {
                replaceAll(ArrayList(it.Keywords))
                notifyDataSetChanged()
            }
        })
    }
}