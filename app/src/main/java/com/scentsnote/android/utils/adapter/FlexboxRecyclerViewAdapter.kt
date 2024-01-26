package com.scentsnote.android.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.KeywordInfo
import com.scentsnote.android.databinding.RvItemFilterFlexboxBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class FlexboxRecyclerViewAdapter(
    internal val select: (KeywordInfo, Boolean) -> Unit,
    private val isOverSelectLimit: () -> Boolean
) : ListAdapter<KeywordInfo, FlexboxRecyclerViewAdapter.FlexboxRecyclerViewHolder>(
    incenseSeriesDiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlexboxRecyclerViewHolder {
        val binding =
            RvItemFilterFlexboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlexboxRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: FlexboxRecyclerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FlexboxRecyclerViewHolder(val binding: RvItemFilterFlexboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: KeywordInfo) {
            binding.rvData = data

            binding.root.setOnSafeClickListener {
                if (!data.checked && isOverSelectLimit()) {
                    Toast.makeText(
                        it.context,
                        R.string.filter_select_over_limit,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnSafeClickListener
                }
                select(data, !data.checked)
                data.checked = !data.checked
                notifyItemChanged(adapterPosition)
            }
        }
    }
}

val incenseSeriesDiffCallback = object : DiffUtil.ItemCallback<KeywordInfo>() {
    override fun areItemsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem.name == newItem.name
    }

}