package com.scents.note.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scents.note.R
import com.scents.note.data.vo.response.KeywordInfo
import com.scents.note.databinding.RvItemFilterFlexboxBinding

class NoteKeywordAdapter(private val type : Int, internal val select:(KeywordInfo, Boolean)->Unit) :
    ListAdapter<KeywordInfo, NoteKeywordAdapter.NoteFlexboxRecyclerViewHolder>(
        NoteKeywordDiffCallback
    ) {
    init {
        setHasStableIds(true)
    }

    companion object{
        const val DIALOG_TYPE = 0
        const val LIST_TYPE = 1
    }

    var data = mutableListOf<KeywordInfo>()
    private lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteFlexboxRecyclerViewHolder {
        val binding =
            RvItemFilterFlexboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteFlexboxRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: NoteFlexboxRecyclerViewHolder, position: Int) {
        when (holder) {
            is NoteFlexboxRecyclerViewHolder -> holder.bind(data[position])
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

    inner class NoteFlexboxRecyclerViewHolder(val binding: RvItemFilterFlexboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: KeywordInfo) {
            binding.rvData = data
            if(data.checked){
                binding.rvItemTxtFlexbox.apply {
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
                    setTextColor(ContextCompat.getColor(this.context, R.color.white))
                }
            }else{
                binding.rvItemTxtFlexbox.apply {
                    background = ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line_square)
                    setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
                }
            }
            when(type){
                DIALOG_TYPE -> {
                    binding.root.setOnClickListener {
                        if (!data.checked) {
                            binding.rvItemTxtFlexbox.apply {
                                select(data,true)
                                data.checked=true
                            }
                        }
                        else {
                            binding.rvItemTxtFlexbox.apply {
                                select(data,false)
                                data.checked=false
                            }
                        }
                        notifyDataSetChanged()
                    }
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

val NoteKeywordDiffCallback = object : DiffUtil.ItemCallback<KeywordInfo>() {
    override fun areItemsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: KeywordInfo, newItem: KeywordInfo): Boolean {
        return oldItem.name == newItem.name
    }

}