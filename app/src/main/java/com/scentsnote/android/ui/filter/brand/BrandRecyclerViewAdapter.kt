package com.scentsnote.android.ui.filter.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.databinding.RvItemFilterBrandBinding

class BrandRecyclerViewAdapter(
    val setSelectedBrand: (BrandInfo, Boolean) -> Unit,
    val countBadge: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<BrandRecyclerViewAdapter.BrandRecyclerViewHolder>() {

    init {
        setHasStableIds(true)
    }

    var data = mutableListOf<BrandInfo>()
    var initial: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandRecyclerViewHolder {
        val binding =
            RvItemFilterBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal fun setData(map: MutableMap<String, MutableList<BrandInfo>>) {
        this.data = map[initial] ?: mutableListOf()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BrandRecyclerViewHolder, position: Int) {

        when (holder) {
            is BrandRecyclerViewHolder -> holder.bind(data[position])
            else -> throw Exception("You Should not attach here")
        }

    }

    inner class BrandRecyclerViewHolder(val binding: RvItemFilterBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: BrandInfo) {
            binding.item = info
            binding.root.setOnClickListener {
                if(info.clickable){
                    info.check = !info.check
                    when (info.check) {
                        true -> {
                            // 선택되었다면, 리스트에 추가
                            countBadge(1, true)
                            setSelectedBrand(info, true)
                        }
                        false -> {
                            //선택 해제 되었다면, 리스트에서 발견 후 삭제
                            countBadge(1, false)
                            setSelectedBrand(info, false)

                        }
                    }
                    notifyDataSetChanged()
                }
                else Toast.makeText(it.context, "5개 이상 선택 할 수 없어요.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

