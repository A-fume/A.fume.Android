package com.scentsnote.android.ui.filter.incense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.SeriesInfo
import com.scentsnote.android.data.vo.response.SeriesIngredient
import com.scentsnote.android.databinding.RvItemFilterSeriesBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class SeriesIngredientsViewAdapter(
    private val selectIngredients: (String, MutableList<SeriesIngredient>) -> Unit,
    private val countBadge: (Int, Boolean) -> Unit
) : ListAdapter<SeriesInfo, SeriesIngredientsViewHolder>(SeriesInfo.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesIngredientsViewHolder {
        val binding =
            RvItemFilterSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesIngredientsViewHolder(binding, selectIngredients, countBadge)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: SeriesIngredientsViewHolder, position: Int) {
        currentList[position].let { holder.bind(it) }
    }
}

class SeriesIngredientsViewHolder(
    val binding: RvItemFilterSeriesBinding,
    private val selectIngredients: (String, MutableList<SeriesIngredient>) -> Unit,
    private val countBadge: (Int, Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SeriesInfo) {
        binding.series = item
        drawIngredients(item.ingredients)
        binding.btnShowIngredients.setOnSafeClickListener {
            foldOrUnfold(it)
        }
    }

    private fun drawIngredients(ingredients: MutableList<SeriesIngredient>) {
        val ingredientAdapter =
            IngredientFlexboxAdapter(ingredients, selectIngredients, countBadge)

        val flexboxLayoutManager = FlexboxLayoutManager(binding.root.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        binding.rvSeriesIngredient.apply {
            itemAnimator = null
            layoutManager = flexboxLayoutManager
            adapter = ingredientAdapter
        }
        ingredientAdapter.submitList(ingredients)
    }

    private fun foldOrUnfold(view: View) {
        if (!view.isSelected) {
            binding.clSeriesIngredient.visibility = View.GONE
            view.isSelected = true
        } else {
            binding.clSeriesIngredient.visibility = View.VISIBLE
            view.isSelected = false
        }
    }
}