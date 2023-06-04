package com.scentsnote.android.ui.filter.incense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.databinding.RvItemFilterSeriesBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel

class SeriesViewAdapter(
    private val viewModel: FilterSeriesViewModel
) : ListAdapter<FilterSeriesViewData.FilterSeriesAllType, SeriesViewHolder>(
    FilterSeriesViewData.FilterSeriesAllType.diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding =
            RvItemFilterSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        currentList[position].let { holder.bind(it) }
    }
}

class SeriesViewHolder(
    val binding: RvItemFilterSeriesBinding,
    private val viewModel: FilterSeriesViewModel
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FilterSeriesViewData.FilterSeriesAllType) {
        binding.series = item
        val ingredients = viewModel.getFilterSeriesIngredientViewData(item.index)
        drawIngredients(ingredients)
        binding.btnShowIngredients.setOnSafeClickListener {
            foldOrUnfold(it)
        }
    }

    private fun drawIngredients(ingredients: List<FilterSeriesViewData>) {
        val ingredientAdapter = IngredientFlexboxAdapter(viewModel)

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