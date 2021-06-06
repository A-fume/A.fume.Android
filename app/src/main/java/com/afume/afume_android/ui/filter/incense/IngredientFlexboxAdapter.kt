package com.afume.afume_android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.SeriesIngredients
import com.afume.afume_android.databinding.RvItemSeriesIngredientsFilterBinding

class IngredientFlexboxAdapter(
    val ingredientsList: MutableList<SeriesIngredients>,
    val setSelectedIngredients: (Int, List<SeriesIngredients>) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) : ListAdapter<SeriesIngredients, IngredientFlexboxAdapter.IngredientFlexboxHolder>(
    seriesIngredientsDiffCallback
) {

    init {
//        ingredientsList.removeAt(0)
    }

    //전체 선택 여부
    var selectedEntire = false
    val selectedIngredients = mutableListOf<SeriesIngredients>()

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

    override fun submitList(list: MutableList<SeriesIngredients>?) {
        super.submitList(list)

        Log.e("submit list", list.toString())
    }


    inner class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientsFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SeriesIngredients) {
            binding.ingredient = data

            Log.d("ingredients data", data.toString())

            binding.root.setOnClickListener {
                data.checked = !data.checked
                binding.ingredient = data
//                isSelectedIngredients(binding.rvItemIngredient,data.checked)

                if (data.ingredientIdx == -1) {
                    //전체를 선택했을 경우

                    for (i in 1..selectedIngredients.size) {
                        countBadge(0, false)
                    }
                    selectedIngredients.clear()

                    val onlyEntireActive = currentList
                    for (i in 1..currentList.lastIndex) {
                        onlyEntireActive[i].checked = false
                    }

                    submitList(onlyEntireActive)
                    notifyDataSetChanged()

                    //전체 선택을 했을 경우, 모든 인덱스 리스트에 추가
                    if (data.checked) {
                        ingredientsList.forEach { selectedIngredients.add(it) }
                        selectedIngredients.removeAt(0) // 전체 키워드 삭제
                        Log.e("전체 선택 추가 add index", it.toString())
                        selectedEntire = true
                        countBadge(0, true)
                    } else {
                        selectedEntire = false
                        countBadge(0, false)
                    }

                } else { // 전체가 아닌 하나의 ingredient를 선택했을 때
                    if (data.checked) {

                        // 전체가 선택이 되어 있는 경우 , 모든 인덱스를 리스트에서 삭제한 후, 선택한 인덱스 추가
                        if (selectedEntire) {

//                            ingredientsList.forEach {
//                                selectedIngredients.remove(data)
//                                Log.e("전체 선택 해제 index remove", it.toString())
//                            }
                            selectedIngredients.clear()
                            selectedEntire = false
                            countBadge(0, false)

                            val ingredientsDataList = currentList
                            ingredientsDataList[0].checked = false

                            submitList(ingredientsDataList)
                            notifyDataSetChanged()

                        }
                        selectedIngredients.add(data)
                        countBadge(0, true)
                        Log.e(" 단일 add index", data.ingredientIdx.toString())

                        Log.e("현재 선택된 ingredients", selectedIngredients.toString())

                    } else {
                        selectedIngredients.remove(data)
                        countBadge(0, false)
                        Log.e("단일 선택 해제 remove index", data.ingredientIdx.toString())
                    }

                    Log.e("현재 선택된 ingredients", selectedIngredients.toString())
                }

                // viewModel로 selectedIngredients 넘기기
                setSelectedIngredients(data.seriesIdx, selectedIngredients)

            }
        }

    }

}

val seriesIngredientsDiffCallback = object : DiffUtil.ItemCallback<SeriesIngredients>() {
    override fun areItemsTheSame(
        oldItem: SeriesIngredients,
        newItem: SeriesIngredients
    ): Boolean {
        return oldItem.ingredientIdx == newItem.ingredientIdx
    }

    override fun areContentsTheSame(
        oldItem: SeriesIngredients,
        newItem: SeriesIngredients
    ): Boolean {
        return oldItem.checked == newItem.checked
    }

}
