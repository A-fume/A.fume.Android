package com.scentsnote.android.ui.filter.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.BrandTab
import com.scentsnote.android.databinding.RvItemFilterBrandTabBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setSelectedTabTxt

class BrandTabRecyclerViewAdapter(private val tabList: MutableList<BrandTab>) : RecyclerView.Adapter<BrandTabRecyclerViewAdapter.BrandTabRecyclerViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

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
            binding.textView39.setSelectedTabTxt(brandTab.selected)
            binding.root.setOnSafeClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                brandTab.selected = !brandTab.selected
            }
        }
    }
}