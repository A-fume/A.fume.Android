package com.afume.afume_android.filter.brand

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.databinding.RvItemFilterBrandBinding
import com.afume.afume_android.filter.RvBrandData
import java.lang.Exception

class BrandRecyclerViewAdapter (private val context: Context?):RecyclerView.Adapter<BrandRecyclerViewAdapter.BrandRecyclerViewHolder>(){
    init {
        setHasStableIds(true)
    }
    var data = listOf<RvBrandData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandRecyclerViewHolder {
        val binding=RvItemFilterBrandBinding.inflate(LayoutInflater.from(context),parent,false,)
        return BrandRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int =data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: BrandRecyclerViewHolder, position: Int) {

        when(holder){
            is BrandRecyclerViewHolder ->holder.bind(data[position],position)
            else-> throw Exception("You Should not attach here")
        }

    }

    inner class BrandRecyclerViewHolder(val binding: RvItemFilterBrandBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(data: RvBrandData, itemPosition:Int){
            binding.data=data
            binding.root.setOnClickListener {
                binding.rvItemTxtBrand.isSelected= !binding.rvItemTxtBrand.isSelected
                when(binding.rvItemTxtBrand.isSelected){
                    true->{
                        // 선택되었다면, 리스트에 추가
                        //activityViewModel.increaseBadgeCount(0)
                    }
                    false->{
                        //선택 해제 되었다면, 리스트에서 발견 후 삭제
                    }
                }
            }
        }

    }
}

