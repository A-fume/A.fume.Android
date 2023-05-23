package com.scentsnote.android.ui.filter

import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.ui.filter.brand.BrandRecyclerViewAdapter
import com.scentsnote.android.ui.filter.incense.FilterSeriesViewData
import com.scentsnote.android.ui.filter.incense.IngredientFlexboxAdapter
import com.scentsnote.android.ui.filter.incense.SeriesViewAdapter

object FilterBinding {

    @JvmStatic
    @BindingAdapter("setSeriesList")
    fun setSeriesList(recyclerView: RecyclerView, list: MutableList<FilterSeriesViewData.FilterSeriesAllType>?) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as SeriesViewAdapter) {
            list?.let {
                submitList(list)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setIngredientList")
    fun setIngredientList(recyclerView: RecyclerView, list: MutableList<FilterSeriesViewData>) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as IngredientFlexboxAdapter) {
            submitList(list)
        }
    }

    @JvmStatic
    @BindingAdapter("setInitialBrandList")
    fun setInitialBrandList(
        recyclerView: RecyclerView,
        map: MutableMap<String, MutableList<BrandInfo>>
    ) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as BrandRecyclerViewAdapter) {
            Log.e("set brand map", map.toString())
            submitList(map[initial] ?: mutableListOf())
        }
    }

    @JvmStatic
    @BindingAdapter("isSelectedIngredient")
    fun isSelectedIngredients(view: TextView, checked: Boolean) {
        if (checked) {
            view.apply {
                setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
                setTextColor(ContextCompat.getColor(this.context, R.color.white))
            }
        } else {
            view.apply {
                background =
                    ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line_square)
                setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
            }
        }
    }

}