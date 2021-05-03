package com.afume.afume_android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.data.vo.response.SeriesIngredients
import com.afume.afume_android.databinding.RvItemFilterSeriesBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SeriesIngredientsViewAdapter : RecyclerView.Adapter<SeriesIngredientsViewHolder>() {

    var data = mutableListOf<SeriesInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesIngredientsViewHolder {
        val binding =
            RvItemFilterSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesIngredientsViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SeriesIngredientsViewHolder, position: Int) {
        data[position].let { holder.bind(it) }
    }

}

class SeriesIngredientsViewHolder(val binding: RvItemFilterSeriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SeriesInfo) {
        binding.series = item
        drawIngredients(item.ingredients)
    }

    fun drawIngredients(ingredients: MutableList<SeriesIngredients>) {

        val ingredientAdapter = IngredientFlexboxAdapter({}, {})

        val flexboxLayoutManager = FlexboxLayoutManager(binding.root.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        binding.rvSeriesIngredient.apply {
            layoutManager = flexboxLayoutManager
            adapter = ingredientAdapter
        }

        //ingredients data 연결넘기기
        Log.d("ingredients", ingredients.toString())
        ingredientAdapter.submitList(ingredients)


    }

}