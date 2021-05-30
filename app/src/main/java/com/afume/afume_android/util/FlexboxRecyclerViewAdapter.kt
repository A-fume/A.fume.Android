package com.afume.afume_android.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.databinding.RvItemFilterFlexboxBinding

class FlexboxRecyclerViewAdapter(internal val select:(KeywordInfo, Boolean)->Unit, val countBadge:(Int,Boolean)->Unit) :
    ListAdapter<KeywordInfo, FlexboxRecyclerViewAdapter.FlexboxRecyclerViewHolder>(
        incenseSeriesDiffCallback
    ) {
    init {
        setHasStableIds(true)
    }

    var data = mutableListOf<KeywordInfo>()
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
            is FlexboxRecyclerViewHolder -> holder.bind(data[position])
            else -> throw Exception("You Should not attach her")
        }
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionTracker = selectionTracker
    }

    internal fun setData(data: MutableList<KeywordInfo>?){
        if(data!=null) this.data=data
        submitList(data)
        notifyDataSetChanged()

    }


    inner class FlexboxRecyclerViewHolder(val binding: RvItemFilterFlexboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: KeywordInfo) {
            binding.rvData = data
            binding.root.setOnClickListener {
                if (!data.checked) {
                    binding.rvItemTxtFlexbox.apply {
                        setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
                        setTextColor(ContextCompat.getColor(this.context, R.color.white))
                        select(data,true)
                        countBadge(2,true)
                        data.checked=true

                    }
                }
                else {
                    binding.rvItemTxtFlexbox.apply {
                        background = ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line_square)
                        setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                        select(data,false)
                        countBadge(2,false)
                        data.checked=false
                    }

//                selectionTracker.select(data.keywordIdx.toLong())
            }

//            if (selectionTracker.isSelected(data.keywordIdx.toLong())) {
//                binding.rvItemTxtFlexbox.apply {
//                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
//                    setTextColor(ContextCompat.getColor(this.context, R.color.white))
//                    add(data.keywordIdx)
//                }
//            }
//            if (!selectionTracker.isSelected(data.keywordIdx.toLong())) {
//                binding.rvItemTxtFlexbox.apply {
//                    background = ContextCompat.getDrawable(
//                        this.context,
//                        R.drawable.border_gray_cd_line_square
//                    )
//                    setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
//                    remove(data.keywordIdx)
//                }


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

val incenseSeriesDiffCallback = object : DiffUtil.ItemCallback<KeywordInfo>() {
    override fun areItemsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem.name == newItem.name
    }

}