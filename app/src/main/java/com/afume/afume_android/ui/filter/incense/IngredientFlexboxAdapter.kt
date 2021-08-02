package com.afume.afume_android.ui.filter.incense

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.SeriesIngredients
import com.afume.afume_android.databinding.RvItemSeriesIngredientsFilterBinding

class IngredientFlexboxAdapter(
    val ingredientsList: MutableList<SeriesIngredients>,
    val setSelectedIngredients: (String, MutableList<SeriesIngredients>) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) : ListAdapter<SeriesIngredients, IngredientFlexboxAdapter.IngredientFlexboxHolder>(
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

    internal fun setList(list: MutableList<SeriesIngredients>?) {
        submitList(list)

        Log.e("submit list", list.toString())
    }


    inner class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientsFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var changeList :MutableList<SeriesIngredients>
        var selectedIngredients = mutableListOf<SeriesIngredients>()

        fun bind(data: SeriesIngredients) {
            binding.ingredient = data
            initSelectedIngredients()
            Log.d("ingredients data", data.toString())

            binding.root.setOnClickListener { it ->
                Log.e("ingredientIdx",data.ingredientIdx.toString())
                if(!data.clickable) Toast.makeText(it.context, "5개 이상 선택 할 수 없어요.", Toast.LENGTH_SHORT).show()
                else {
                    data.checked = !data.checked
                    if (data.ingredientIdx == -1) { //전체를 선택했을 경우

                        // 전체선택 활성화, 모든 버튼 비활성화 후 인덱스 리스트에 추가
                        if (data.checked) {
                            setInactiveAll()
                            ingredientsList.forEach { selectedIngredients.add(it) }
                            countBadge(0, true)
                        }
                        // 전체선택 비활성화
                        else setInactiveEntire()
                    }
                    else { // 전체가 아닌 하나의 ingredient를 선택했을 때 선택한 인덱스 추가
                        if (data.checked) {
                            // 전체 선택이 되어 있는 경우, 전체 선택 비활성화
                            if (currentList[0].checked) setInactiveEntire()

                            selectedIngredients.add(data)
                            countBadge(0, true)
                        }
                        else {
                            selectedIngredients.remove(data)
                            countBadge(0, false)
                        }
                    }

                    // viewModel로 selectedIngredients 넘기기
                    setSelectedIngredients(data.seriesName+" 전체", selectedIngredients)
                    Log.e("selectedIngredients__", selectedIngredients.toString())
                }
            }

        }

        fun initSelectedIngredients(){
            selectedIngredients.clear()
            changeList=currentList
            changeList.forEach {
                if(it.checked) selectedIngredients.add(it)
            }
        }
        fun setInactiveEntire(){
            //계열 전체 버튼 비활성화
            changeList = currentList
            changeList[0].checked = false
            setList(changeList)
            countBadge(0, false)
            selectedIngredients.clear()
        }
        fun setInactiveAll(){
            //각 계열의 모든 버튼 비활성화
            changeList = currentList
            for (i in 1..currentList.lastIndex) {
                if(changeList[i].checked) {
                    changeList[i].checked = false
                    countBadge(0,false)
                }
            }
            setList(changeList)
            selectedIngredients.clear()
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
