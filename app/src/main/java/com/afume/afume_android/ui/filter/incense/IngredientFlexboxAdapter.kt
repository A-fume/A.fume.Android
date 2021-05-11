package com.afume.afume_android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.SeriesIngredients
import com.afume.afume_android.databinding.RvItemSeriesIngredientsFilterBinding

class IngredientFlexboxAdapter(val add: (Int) -> Unit, val remove: (Int) -> Unit) :
    ListAdapter<SeriesIngredients, IngredientFlexboxHolder>(
        seriesIngredientsDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientFlexboxHolder {
        val binding = RvItemSeriesIngredientsFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientFlexboxHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientFlexboxHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientsFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SeriesIngredients) {
        binding.ingredient = data
        Log.d("ingredients data", data.toString())
        binding.root.setOnClickListener {
            data.checked = setActive(data.checked)
        }
    }

    fun setActive(checked: Boolean): Boolean {
        if (!checked) {
            binding.rvItemIngredient.apply {
                setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
                setTextColor(ContextCompat.getColor(this.context, R.color.white))
                // add(data.keywordIdx)
            }
            return true
        } else {
            binding.rvItemIngredient.apply {
                binding.rvItemIngredient.apply {
                    background = ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line_square)
                    setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                    //remove(data.keywordIdx)
                }
            }
            return false
        }
    }
}

val seriesIngredientsDiffCallback = object : DiffUtil.ItemCallback<SeriesIngredients>() {
    override fun areItemsTheSame(
        oldItem: SeriesIngredients,
        newItem: SeriesIngredients
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SeriesIngredients,
        newItem: SeriesIngredients
    ): Boolean {
        return oldItem.ingredientIdx == newItem.ingredientIdx
    }

}
