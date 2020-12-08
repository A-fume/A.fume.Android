package com.afume.afume_android.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.databinding.RvItemFilterFlexboxBinding
import java.lang.Exception

class FlexboxRecyclerViewAdapter :
    ListAdapter<RvFlexboxData, FlexboxRecyclerViewAdapter.FlexboxRecyclerViewHolder>(
        incenseSeriesDiffCallback
    ) {
    init {
        setHasStableIds(true)
    }

    var data = listOf<RvFlexboxData>()
    private lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlexboxRecyclerViewHolder {
        val binding =
            RvItemFilterFlexboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlexboxRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: FlexboxRecyclerViewHolder, position: Int) {
        when (holder) {
            is FlexboxRecyclerViewHolder -> holder.bind(data[position], position)
            else -> throw Exception("You Should not attach her")
        }
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionTracker = selectionTracker
    }


    inner class FlexboxRecyclerViewHolder(val binding: RvItemFilterFlexboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RvFlexboxData, itemPosition: Int) {
            binding.rvData = data
            binding.root.setOnClickListener {
                selectionTracker.select(itemPosition.toLong())
            }
            if (selectionTracker.isSelected(itemPosition.toLong())) {
                binding.rvItemTxtFlexbox.apply {
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
                    setTextColor(ContextCompat.getColor(this.context, R.color.white))
                }
            }
            if (!selectionTracker.isSelected(itemPosition.toLong())) {
                binding.rvItemTxtFlexbox.apply {
                    background = ContextCompat.getDrawable(
                        this.context,
                        R.drawable.border_gray_cd_line_square
                    )
                    setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                }


            }
        }

        fun getItemDetails(viewHolder: RecyclerView.ViewHolder?): ItemDetailsLookup.ItemDetails<Long> {
            return object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getSelectionKey(): Long? {
                    return itemId
                }

                override fun getPosition(): Int {
                    if (viewHolder == null) {
                        return RecyclerView.NO_POSITION
                    }
                    return viewHolder.adapterPosition
                }

            }
        }
    }
}

val incenseSeriesDiffCallback = object : DiffUtil.ItemCallback<RvFlexboxData>() {
    override fun areItemsTheSame(oldItem: RvFlexboxData, newItem: RvFlexboxData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RvFlexboxData, newItem: RvFlexboxData): Boolean {
        return oldItem.flexboxTxt == newItem.flexboxTxt
    }

}