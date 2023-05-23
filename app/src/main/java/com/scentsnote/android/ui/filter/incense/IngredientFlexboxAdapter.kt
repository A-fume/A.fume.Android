package com.scentsnote.android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.databinding.RvItemSeriesIngredientFilterBinding
import com.scentsnote.android.utils.extension.copy
import com.scentsnote.android.utils.extension.isAllType
import com.scentsnote.android.utils.extension.isSameContent
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel

class IngredientFlexboxAdapter(
    val viewModel: FilterSeriesViewModel
) : ListAdapter<FilterSeriesViewData, IngredientFlexboxAdapter.IngredientFlexboxHolder>(
    FilterSeriesViewData.diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientFlexboxHolder {
        val binding = RvItemSeriesIngredientFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientFlexboxHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientFlexboxHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun hasSelectedNormalType() =
            currentList.subList(1, itemCount - 1).count { it.isChecked } > 0

        private fun checkCanSelect(ingredient: FilterSeriesViewData): Boolean {
            val isOverSelectLimit = viewModel.isOverSelectLimit()
            val isDeselect = ingredient.isChecked
            val canSelectAllType = ingredient.isAllType && hasSelectedNormalType()
            val canSelectNormalType = !ingredient.isAllType && currentList[0].isChecked
            return !isOverSelectLimit || isDeselect || canSelectAllType || canSelectNormalType
        }

        fun bind(data: FilterSeriesViewData) {
            initCheckedState(data)
            binding.ingredient = data

            binding.root.setOnSafeClickListener {
                Log.d(TAG, "onClicked ingredientIdx: ${data.index}")

                if (!checkCanSelect(data)) {
                    Toast.makeText(
                        it.context,
                        R.string.filter_select_over_limit,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnSafeClickListener
                }

                val newItems = mutableListOf<FilterSeriesViewData>()
                when (data) {
                    is FilterSeriesViewData.FilterSeriesAllType -> {
                        viewModel.onSelectAllType(data, newItems, currentList)
                    }

                    is FilterSeriesViewData.FilterSeriesIngredient -> {
                        viewModel.onSelectIngredientType(data, newItems, currentList[0])
                    }
                }
                replaceList(newItems)
            }
        }

        private fun initCheckedState(data: FilterSeriesViewData) {
            if (viewModel.selectedSeriesList.contains(data)) {
                replaceList(data.copy(isChecked = true))
            }
        }

        private fun replaceList(newItems: List<FilterSeriesViewData>) {
            if (newItems.isEmpty()) return

            val tempList: MutableList<FilterSeriesViewData> = currentList.toMutableList()
            newItems.forEach { newItem ->
                val index = tempList.indexOfFirst { it.isSameContent(newItem) }
                tempList[index] = newItem
            }
            submitList(tempList)
        }

        private fun replaceList(newItem: FilterSeriesViewData) {
            replaceList(listOf(newItem))
        }
    }

    companion object {
        private const val TAG = "IngredientFlexboxAdapter"
    }
}