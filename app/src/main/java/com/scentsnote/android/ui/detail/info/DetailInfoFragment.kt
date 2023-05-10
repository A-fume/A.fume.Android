package com.scentsnote.android.ui.detail.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentDetailInfoBinding
import com.scentsnote.android.ui.detail.PerfumeDetailViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.utils.adapter.BindingAdapter.setDetailInfoText
import com.scentsnote.android.utils.base.BaseWebViewActivity

/**
 * 향수 상세 페이지 - 왼쪽 탭(향수 정보)
 *
 * 해당 향수의 상세 정보(조향스토리, 키워드, 노트, 부향률, 가격 및 용량, 계절감, 지속력, 잔향감, 성별감, 비슷한 향수 등)를 표시
 */

class DetailInfoFragment(val perfumeIdx: Int) : Fragment() {

    lateinit var binding: FragmentDetailInfoBinding
    lateinit var rvKeywordAdapter: DetailKeywordAdapter
    private val rvPriceAdapter = PriceRvAdapter()
    private lateinit var rvSimilarAdapter: SimilarListAdapter
    lateinit var chartLastingPowerAdapter: HorizontalBarChartAdapter
    private val detailViewModel: PerfumeDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
        binding.fm = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.getPerfumeInfo(perfumeIdx)
        detailViewModel.getSimilarPerfumeList(perfumeIdx)
        observe()

        initRv(context)
        initLastingPowerBarChart(0, 0, 0, 0, 0)
        initsillageBarChart(0, 0, 0)
        initRvSimilar(requireContext())
    }

    /** 키워드 */
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

    /** 비슷한 향수 */
    private fun initRvSimilar(ctx: Context) {
        rvSimilarAdapter = SimilarListAdapter(
            requireContext(),
            parentFragmentManager
        ) { idx -> detailViewModel.postSimilarPerfumeLike(idx) }
        binding.rvDetailsInfoSimilar.adapter = rvSimilarAdapter
        rvSimilarAdapter.notifyDataSetChanged()
    }

    /** 지속력 - 막대 차트(세로) */
    private fun initLastingPowerBarChart(
        veryWeak: Int,
        weak: Int,
        medium: Int,
        strong: Int,
        veryStrong: Int
    ) {
        chartLastingPowerAdapter = HorizontalBarChartAdapter(0, context)
        binding.chartBarDetailsInfoLastingPower.adapter = chartLastingPowerAdapter
        chartLastingPowerAdapter.chartData = listOf(
            veryWeak.toFloat(),
            weak.toFloat(),
            medium.toFloat(),
            strong.toFloat(),
            veryStrong.toFloat()
        )
        chartLastingPowerAdapter.notifyDataSetChanged()
    }

    /** 잔향감 - 막대 그래프(가로) */
    private fun initsillageBarChart(light: Int, medium: Int, heavy: Int) {
        val sillageAdapter = HorizontalBarChartAdapter(1, context)
        binding.chartBarDetailsInfoSillage.adapter = sillageAdapter
        sillageAdapter.chartData = listOf(light.toFloat(), medium.toFloat(), heavy.toFloat())
        sillageAdapter.notifyDataSetChanged()
    }

    /** 성별감 - 원형 그래프 */
    private fun drawGenderPieChart(pieDataSet: PieDataSet) {
        val pieDataColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.point_beige_accent),
            ContextCompat.getColor(requireContext(), R.color.point_beige),
            ContextCompat.getColor(requireContext(), R.color.point_dark_beige)
        )
        val pieValuesTextColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
        )
        pieDataSet.apply {
            colors = pieDataColors

//            setDrawValues(true) // 라벨 노출 여부
//            sliceSpace = 1f // 차트 사이 여백

            setValueTextColors(pieValuesTextColors)
//            valueFormatter = object: ValueFormatter(){
//                override fun getFormattedValue(value: PieEntry): String {
//                    return value.toInt().toString() + "개"
//                }
//            }
        }

        val pieData = PieData(pieDataSet)

        setPieChart(binding.chartPieDetailsInfoGender, pieData)
        setPieLegend(binding.chartPieDetailsInfoGender.legend)
    }

    private fun setGenderPieData(female: Float, male: Float, middle: Float): PieDataSet {

        val pieListData = mutableListOf(
            PieEntry(female, "여성"),
            PieEntry(male, "남성"),
            PieEntry(middle, "중성"),
        )

        val iter = pieListData.iterator()
        while (iter.hasNext()) {
            if (iter.next().value == 0f) {
                iter.remove()
            }
        }

        val pieData = listOf(female, male, middle)
        val maxPieData = pieData.maxOrNull()

        setMaxLegendBold(
            female,
            maxPieData,
            binding.txtDetailGenderFemale,
            binding.txtDetailGenderFemalePercent
        )
        setMaxLegendBold(
            male,
            maxPieData,
            binding.txtDetailGenderMale,
            binding.txtDetailGenderMalePercent
        )
        setMaxLegendBold(
            middle,
            maxPieData,
            binding.txtDetailGenderMiddle,
            binding.txtDetailGenderMiddlePercent
        )

        return PieDataSet(pieListData, "")
    }

    private fun setMaxLegendBold(
        data: Float,
        maxData: Float?,
        legend: TextView,
        percent: TextView
    ) {
        if (data == maxData) {
            setBoldLegend(legend)
            setBoldLegend(percent)
        }
    }

    /** 계절감 - 원형 그래프 */
    private fun drawSeasonPieChart(pieDataSet: PieDataSet) {
        val pieDataColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.point_beige_accent),
            ContextCompat.getColor(requireContext(), R.color.point_beige),
            ContextCompat.getColor(requireContext(), R.color.point_pinkish_grey),
            ContextCompat.getColor(requireContext(), R.color.point_dark_beige)
        )
        val pieValuesTextColors = listOf<Int>(
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.white),
        )
        pieDataSet.apply {
            colors = pieDataColors

            setValueTextColors(pieValuesTextColors)
        }

        val pieData = PieData(pieDataSet)

        setPieChart(binding.chartPieDetailsInfoSeason, pieData)
        setPieLegend(binding.chartPieDetailsInfoSeason.legend)
    }

    private fun setSeasonPieData(
        spring: Float,
        summer: Float,
        fall: Float,
        winter: Float
    ): PieDataSet {

        val pieListData = mutableListOf(
            PieEntry(spring, "봄"),
            PieEntry(summer, "여름"),
            PieEntry(fall, "가을"),
            PieEntry(winter, "겨울")
        )

        val iter = pieListData.iterator()
        while (iter.hasNext()) {
            if (iter.next().value == 0f) {
                iter.remove()
            }
        }

        val pieData = listOf(spring, summer, fall, winter)
        val maxPieData = pieData.maxOrNull()

        setMaxLegendBold(
            spring,
            maxPieData,
            binding.txtDetailSeasonSpring,
            binding.txtDetailSeasonSpringPercent
        )
        setMaxLegendBold(
            summer,
            maxPieData,
            binding.txtDetailSeasonSummer,
            binding.txtDetailSeasonSummerPercent
        )
        setMaxLegendBold(
            fall,
            maxPieData,
            binding.txtDetailSeasonFall,
            binding.txtDetailSeasonFallPercent
        )
        setMaxLegendBold(
            winter,
            maxPieData,
            binding.txtDetailSeasonWinter,
            binding.txtDetailSeasonWinterPercent
        )

        return PieDataSet(pieListData, "")
    }

    private fun setPieChart(pieChart: PieChart, pieData: PieData) {
        pieChart.apply {
//            setEntryLabelColor(ContextCompat.getColor(requireContext(),R.color.dark_gray_7d))
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                setLeftTopRightBottom(0,5,10,5)
//            }

            setDrawCenterText(false)
            setEntryLabelTextSize(14f)
            setEntryLabelTypeface(ResourcesCompat.getFont(this.context, R.font.notosans_regular))
//            setDrawMarkers(true)
            setTouchEnabled(false) // 그래프 터치 (확대, 회전 등)
            setUsePercentValues(false) // 그래프 안에 텍스트를 퍼센트로 표기할건지 여부
            animateY(2500, Easing.EaseInOutCubic)
            description.isEnabled = false // 그래프 설명(이름?)
            isDrawHoleEnabled = false // 가운데를 뚫을건지 여부

            minOffset = 0f
            data = pieData
            data.setDrawValues(false)
            invalidate()
        }
    }

    private fun setPieLegend(pieLegend: Legend) {
        pieLegend.isEnabled = false
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

    private fun setBoldLegend(legend: TextView) {
        legend.setTextColor(ResourcesCompat.getColor(this.resources, R.color.primary_black, null))
        legend.typeface = ResourcesCompat.getFont(requireContext(), R.font.notosans_bold)
    }

    private fun observe() {
        detailViewModel.perfumeDetailData.observe(requireActivity(), Observer {
            binding.run {
                data = it
                txtDetailsInfoStory.setDetailInfoText(it.story)
                txtCategory.setDetailInfoText(it.abundanceRate)
                setVisibilityMore()

                initLastingPowerBarChart(
                    it.longevity.veryWeak,
                    it.longevity.weak,
                    it.longevity.medium,
                    it.longevity.strong,
                    it.longevity.veryStrong
                )
                initsillageBarChart(it.sillage.light, it.sillage.medium, it.sillage.heavy)
                drawGenderPieChart(
                    setGenderPieData(
                        it.gender.female.toFloat(),
                        it.gender.male.toFloat(),
                        it.gender.neutral.toFloat()
                    )
                )
                drawSeasonPieChart(
                    setSeasonPieData(
                        it.seasonal.spring.toFloat(),
                        it.seasonal.summer.toFloat(),
                        it.seasonal.fall.toFloat(),
                        it.seasonal.winter.toFloat()
                    )
                )
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

    /** 더보기 버튼 : 조향 스토리 3줄 이상일 경우에는 더보기 버튼 표시 */
    private fun setVisibilityMore() {
        val lineCount = binding.txtDetailsInfoStory.layout.lineCount
        if (lineCount > 3) {
            binding.txtDetailsMore.visibility = View.VISIBLE

            binding.txtDetailsMore.setOnClickListener {
                if (binding.txtDetailsInfoStory.maxLines > 3) {
                    binding.txtDetailsInfoStory.maxLines = 3
                    binding.clDetailsMore.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.background_btn_details_more
                    )
                    binding.txtDetailsMore.text = "더보기"
                    binding.txtDetailsMore.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(requireContext(), R.drawable.icon_btn_down),
                        null
                    )
                } else {
                    binding.txtDetailsInfoStory.maxLines = Int.MAX_VALUE
                    binding.clDetailsMore.background =
                        ContextCompat.getDrawable(requireContext(), R.color.transparent)
                    binding.txtDetailsMore.text = "접기"
                    binding.txtDetailsMore.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(requireContext(), R.drawable.icon_btn_up),
                        null
                    )
                }
            }
        } else {
            binding.txtDetailsInfoStory.setPadding(0, 0, 0, 0)
        }
    }

    /** 정보 수정 제안 버튼 - 현재(2023.05) 구글폼으로 연결 */
    fun onClickInfoReportBtn(view: View){
        val intent = Intent(activity, BaseWebViewActivity::class.java)
        intent.putExtra("url", "infoReport")
        startActivity(intent)
    }
}