package com.afume.afume_android.ui.detail.info

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.databinding.RvItemDetailsInfoHorizontalBarChartBinding
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class HorizontalBarChartAdapter(val type: Int, val context: Context?) :
    RecyclerView.Adapter<HorizontalBarChartHolder>() {
    var chartData = listOf<Float>()
    var maxIndex :Int?= 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalBarChartHolder {
        val binding = RvItemDetailsInfoHorizontalBarChartBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return HorizontalBarChartHolder(binding)
    }

    override fun getItemCount(): Int = chartData.size

    override fun onBindViewHolder(holder: HorizontalBarChartHolder, position: Int) {
        maxIndex= chartData.indexOf(chartData.maxOrNull())

        holder.bind(chartData[position], type, position, context,maxIndex)
        holder.binding.rvItemChartBarHorizontal.invalidate()
    }

}

class HorizontalBarChartHolder(val binding: RvItemDetailsInfoHorizontalBarChartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val lastingPowerLabels = listOf<String>(
        "매우 약함",
        "약함",
        "보통",
        "강함",
        "매우 강함"
    )
    val sillageLabels = listOf<String>(
        "가벼움",
        "보통",
        "무거움"
    )

    fun bind(data: Float, type: Int, position: Int, context: Context?, maxIndex:Int?) {
        var isMax=false
        if(position==maxIndex){
            val notosansBold=ResourcesCompat.getFont(context!!,R.font.notosans_bold)
            binding.rvItemLabel.apply {
                typeface = notosansBold
                setTextColor( ContextCompat.getColor(context, R.color.primary_black))
            }
            binding.rvItemTxtPercentage.apply {
                typeface = notosansBold
                setTextColor( ContextCompat.getColor(context, R.color.primary_black))
            }
            isMax=true
        }

        when (type) {
            0 -> binding.rvItemLabel.text = lastingPowerLabels[position]
            1 -> binding.rvItemLabel.text = sillageLabels[position]
        }
        initLastingPowerBarChart(binding.rvItemChartBarHorizontal, data, context,isMax)
        binding.rvItemTxtPercentage.text = data.toInt().toString() + "%"
        binding.rvItemChartBarHorizontal.invalidate()
        binding.rvItemChartBarHorizontal.animateY(5000)


    }

    private fun initLastingPowerBarChart(chart: HorizontalBarChart, data: Float, context: Context?, isMax: Boolean) {
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setDrawBarShadow(true)
        chart.isDragEnabled = false
        chart.isDoubleTapToZoomEnabled=false
        chart.isClickable=false


        val xAxis = chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawLabels(false)

        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawLabels(false)

        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 100f


        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false

        chart.data = context?.let { getData(data, it,isMax) }
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        chart.invalidate()
    }

    private fun getData(data: Float, context: Context, isMax: Boolean): BarData {
        val barEntry = listOf<BarEntry>(BarEntry(1f, data))
        val dataSet = BarDataSet(barEntry, "")

        dataSet.barShadowColor = ContextCompat.getColor(context, R.color.light_gray_f0)

        if(isMax) dataSet.color=ContextCompat.getColor(context, R.color.point_beige_accent)
        else dataSet.color=ContextCompat.getColor(context, R.color.point_beige)

        dataSet.setDrawValues(false)

        val barData = BarData(dataSet)
        barData.barWidth = 1f
        return barData
    }

}