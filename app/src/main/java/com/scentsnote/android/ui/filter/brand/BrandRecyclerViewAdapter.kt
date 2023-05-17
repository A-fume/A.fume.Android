package com.scentsnote.android.ui.filter.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.BrandInfo
import com.scentsnote.android.databinding.RvItemFilterBrandBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.viewmodel.filter.BrandViewModel

class BrandRecyclerViewAdapter(
    private val viewModel: BrandViewModel
) : ListAdapter<BrandInfo, BrandRecyclerViewAdapter.BrandRecyclerViewHolder>(BrandInfo.diffUtil) {

    init {
        setHasStableIds(true)
    }

    var initial: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandRecyclerViewHolder {
        val binding =
            RvItemFilterBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: BrandRecyclerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class BrandRecyclerViewHolder(val binding: RvItemFilterBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: BrandInfo) {
            binding.item = info
            binding.root.setOnSafeClickListener {
                if (!info.check && viewModel.isOverSelectLimit()) {
                    val message = it.context.getString(R.string.filter_select_over_limit)
                    Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
                    return@setOnSafeClickListener
                }

                viewModel.setSelectedBrandListIdx(info, !info.check)
                info.check = !info.check
                notifyItemChanged(adapterPosition)
            }
        }

    }
}

