package com.scentsnote.android.ui.filter

import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.data.vo.response.SeriesInfo
import com.scentsnote.android.data.vo.response.SeriesIngredients
import com.scentsnote.android.ui.filter.brand.BrandRecyclerViewAdapter
import com.scentsnote.android.ui.filter.incense.IngredientFlexboxAdapter
import com.scentsnote.android.ui.filter.incense.SeriesIngredientsViewAdapter

object FilterBinding {

    @JvmStatic
    @BindingAdapter("setSeriesIngredientList")
    fun setSeriesIngredientList(recyclerView: RecyclerView, list: MutableList<SeriesInfo>?) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as SeriesIngredientsViewAdapter) {
            list?.let {
                setSeriesData(list)
                Log.e("setseriesList", list.toString())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setIngredientList")
    fun setIngredientList(recyclerView: RecyclerView, list: MutableList<SeriesIngredients>?) {
        if (recyclerView.adapter != null) with(recyclerView.adapter as IngredientFlexboxAdapter) {
            this.setList(list)
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
            this.setData(map)
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