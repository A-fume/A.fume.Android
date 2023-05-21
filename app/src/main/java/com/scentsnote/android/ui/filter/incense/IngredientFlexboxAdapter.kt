package com.scentsnote.android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.SeriesIngredient
import com.scentsnote.android.databinding.RvItemSeriesIngredientFilterBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class IngredientFlexboxAdapter(
    val ingredientsList: MutableList<SeriesIngredient>,
    val setSelectedIngredients: (String, MutableList<SeriesIngredient>) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) : ListAdapter<SeriesIngredient, IngredientFlexboxAdapter.IngredientFlexboxHolder>(
    SeriesIngredient.diffUtil
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
        holder.bind(getItem(position))
    }

    inner class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var changeList: MutableList<SeriesIngredient>
        val selectedIngredients = mutableListOf<SeriesIngredient>()

        fun bind(seriesIngredient: SeriesIngredient) {
            binding.ingredient = seriesIngredient
            initSelectedIngredients()
            Log.d(TAG, "ingredients data: $seriesIngredient")

            // 그려줄때, 전체가 선택 되어있는 경우 다른 재료들 비활성화
            if (seriesIngredient.checked && seriesIngredient.ingredientIdx <= -1) { //전체를 선택했을 경우
                changeList = currentList
                for (i in 1..currentList.lastIndex) {
                    if (changeList[i].checked) {
                        changeList[i].checked = false
                    }
                }
            }

            binding.root.setOnSafeClickListener setOnClickListener@{ it ->
                Log.d(TAG, "onClicked ingredientIdx: ${seriesIngredient.ingredientIdx}")
                if (!seriesIngredient.clickable) {
                    Toast.makeText(
                        it.context,
                        "5개 이상 선택 할 수 없어요.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val newItem = seriesIngredient.copy(checked = !seriesIngredient.checked)
                val isSelectAllType = newItem.ingredientIdx <= -1
                if (isSelectAllType) {
                    // 전체선택 활성화, 모든 버튼 비활성화 후 인덱스 리스트에 추가
                    if (newItem.checked) {
                        setInactiveAll()
                        ingredientsList.forEach { selectedIngredients.add(it) }
                        countBadge(0, true)
                    }
                    // 전체선택 비활성화
                    else setInactiveEntire()
                } else { // 전체가 아닌 하나의 ingredient를 선택했을 때 선택한 인덱스 추가
                    if (newItem.checked) {
                        // 전체 선택이 되어 있는 경우, 전체 선택 비활성화
                        if (currentList[0].checked) setInactiveEntire()
                        selectedIngredients.add(newItem)
                        countBadge(0, true)
                    } else {
                        selectedIngredients.remove(newItem)
                        countBadge(0, false)
                    }
                }

                // viewModel로 selectedIngredients 넘기기
                setSelectedIngredients(seriesIngredient.seriesName + " 전체", selectedIngredients)
                Log.d(TAG, "selectedIngredients: $selectedIngredients")
                replaceList(newItem)
            }
        }

        private fun replaceList(newItem: SeriesIngredient) {
            submitList(
                currentList.map { oldItem ->
                    if (oldItem.ingredientIdx == newItem.ingredientIdx) {
                        newItem
                    } else {
                        oldItem
                    }
                }
            )
        }

        private fun initSelectedIngredients() {
            selectedIngredients.clear()
            changeList = currentList
            changeList.forEach {
                if (it.checked) selectedIngredients.add(it)
            }
        }

        private fun setInactiveEntire() {
            //계열 전체 버튼 비활성화
            changeList = currentList
            changeList[0].checked = false
            countBadge(0, false)
            selectedIngredients.clear()
        }

        private fun setInactiveAll() {
            //각 계열의 모든 버튼 비활성화
            changeList = currentList
            for (i in 1..currentList.lastIndex) {
                if (changeList[i].checked) {
                    changeList[i].checked = false
                    countBadge(0, false)
                }
            }
            selectedIngredients.clear()
        }

    }

    companion object {
        private val TAG = "IngredientFlexboxAdapter"
    }
}