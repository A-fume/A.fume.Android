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

class IngredientFlexboxAdapter(
    val ingredientsList: MutableList<Int>,
    val add: (Int) -> Unit,
    val remove: (Int) -> Unit
) : ListAdapter<SeriesIngredients, IngredientFlexboxAdapter.IngredientFlexboxHolder>(
    seriesIngredientsDiffCallback
) {

    init {
        ingredientsList.removeAt(0)
    }

    //전체 선택 여부
    var selectedEntire = false
    var ingredientsDataList= mutableListOf<SeriesIngredients>()
    val selectedIngredients = mutableListOf<Int>()

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

//    override fun submitList(list: MutableList<SeriesIngredients>?) {
//        if (list != null) {
//            ingredientsDataList=list
//        }
//
//        super.submitList(ingredientsDataList)
//    }
//
//    fun setList(list: MutableList<SeriesIngredients>){
//        submitList(list)
//    }


    inner class IngredientFlexboxHolder(val binding: RvItemSeriesIngredientsFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SeriesIngredients) {
            binding.ingredient = data

            Log.d("ingredients data", data.toString())

            binding.root.setOnClickListener {
                data.checked = setActive(data.checked)

                if (data.ingredientIdx == -1) {
                    //전체 선택을 했을 경우, 모든 인덱스 리스트에 추가
                    ingredientsList.forEach {
                        if (data.checked) {
                            selectedIngredients.add(it)
                            Log.e("전체 선택 추가 add index", it.toString())
                            add(it)
                        } else {
                            selectedIngredients.remove(it)
                            Log.e("전체 선택 추가 remove index", it.toString())
                            remove(it)
                        }
                    }

                    Log.e("현재 선택된 ingredients", selectedIngredients.toString())
                    selectedEntire = true
                } else {
                    if (data.checked) {

                        // 전체가 선택이 된 경우 , 모든 인덱스를 리스트에서 삭제한 후, 선택한 인덱스 추가
                        if (selectedEntire) {
                            ingredientsList.forEach {
                                remove(it)
                                selectedIngredients.remove(it)
                                Log.e("전체 선택 해제 index remove", it.toString())
                            }
                            selectedEntire=false
//                            ingredientsDataList[0].checked=false
//
//                            setList(ingredientsDataList)
                        }
                        add(data.ingredientIdx)
                        selectedIngredients.add(data.ingredientIdx)
                        Log.e(" 단일 add index", data.ingredientIdx.toString())

                        Log.e("현재 선택된 ingredients", selectedIngredients.toString())
                        //todo 전체칸 active 상태가 풀려야 함.
                    } else {
                        remove(data.ingredientIdx)
                        selectedIngredients.remove(data.ingredientIdx)
                        Log.e("단일 선택 해제 remove index", data.ingredientIdx.toString())
                    }
                    Log.e("현재 선택된 ingredients", selectedIngredients.toString())
                }
                //todo 개별 재료 선택 후 전체 선태하면 개별 재료 선택 해제


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
                        background = ContextCompat.getDrawable(
                            this.context,
                            R.drawable.border_gray_cd_line_square
                        )
                        setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                        //remove(data.keywordIdx)
                    }
                }
                return false
            }

        }

}}

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
