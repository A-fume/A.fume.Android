package com.scentsnote.android.ui.filter.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.BrandTab
import com.scentsnote.android.databinding.RvItemFilterBrandTabBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setSelectedTabTxt
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel

class BrandTabRecyclerViewAdapter(private val tabList: MutableList<BrandTab>, private val viewModel: FilterBrandViewModel) : RecyclerView.Adapter<BrandTabRecyclerViewAdapter.BrandTabRecyclerViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null
    var currentAdapterPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandTabRecyclerViewHolder {
        val binding =
            RvItemFilterBrandTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandTabRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = tabList.size

    override fun onBindViewHolder(holder: BrandTabRecyclerViewHolder, position: Int) {
        holder.bind(tabList[position])
    }

    inner class BrandTabRecyclerViewHolder(val binding: RvItemFilterBrandTabBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brandTab: BrandTab) {
            binding.item = brandTab
            binding.tabName.setSelectedTabTxt(brandTab.isSelected)
            brandTab.isSelected = false

            binding.root.setOnSafeClickListener {
                itemClickListener?.onItemClick(adapterPosition)
                brandTab.isSelected = true

                notifyItemChanged(adapterPosition)
                notifyItemChanged(currentAdapterPosition)
                currentAdapterPosition = adapterPosition
            }
        }
    }
}