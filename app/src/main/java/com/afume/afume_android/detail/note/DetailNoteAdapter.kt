package com.afume.afume_android.detail.note

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.DetailNoteListData
import com.afume.afume_android.databinding.RvItemDetailNoteBinding

class DetailNoteAdapter(private val context: Context) : RecyclerView.Adapter<DetailNoteViewHolder>() {
    var data = mutableListOf<DetailNoteListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailNoteViewHolder {
        val binding: RvItemDetailNoteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_note,
            parent,
            false
        )

        return DetailNoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailNoteViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size
}

class DetailNoteViewHolder(val binding: RvItemDetailNoteBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item : DetailNoteListData){
        binding.item = item
    }
}