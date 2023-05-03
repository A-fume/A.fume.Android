package com.scentsnote.android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.SeriesInfo
import com.scentsnote.android.data.vo.response.SeriesIngredients
import com.scentsnote.android.databinding.RvItemFilterSeriesBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class SeriesIngredientsViewAdapter(
    val selectIngredients: (String, MutableList<SeriesIngredients>) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<SeriesIngredientsViewHolder>() {

    var data = mutableListOf<SeriesInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesIngredientsViewHolder {
        val binding =
            RvItemFilterSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesIngredientsViewHolder(binding, selectIngredients, countBadge)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SeriesIngredientsViewHolder, position: Int) {
        data[position].let { holder.bind(it) }
    }

    internal fun setSeriesData(data: MutableList<SeriesInfo>?) {
        if (data != null) this.data = data
        notifyDataSetChanged()
    }

}

class SeriesIngredientsViewHolder(
    val binding: RvItemFilterSeriesBinding,
    val selectIngredients: (String, MutableList<SeriesIngredients>) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SeriesInfo) {
        binding.series = item
        Log.d("ingredients item", item.ingredients.toString())
        drawIngredients(item.ingredients)
        binding.btnShowIngredients.setOnSafeClickListener {
            foldORUnfold(it)
        }
    }

    fun drawIngredients(ingredients: MutableList<SeriesIngredients>) {

//        val itsIngredient = mutableListOf<Int>()
//        ingredients.forEach { itsIngredient.add(it.ingredientIdx) }

        val ingredientAdapter =
            IngredientFlexboxAdapter(ingredients, selectIngredients, countBadge)

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
//        Log.d("ingredients", ingredients.toString())
        ingredientAdapter.submitList(ingredients)

    }

    fun foldORUnfold(view: View) {
        if (!view.isSelected) {
            binding.clSeriesIngredient.visibility = View.GONE
            view.isSelected = true
        } else {
            binding.clSeriesIngredient.visibility = View.VISIBLE
            view.isSelected = false
        }

    }

}