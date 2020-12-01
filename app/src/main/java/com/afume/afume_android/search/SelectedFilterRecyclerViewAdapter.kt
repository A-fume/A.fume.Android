package com.afume.afume_android.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.databinding.RvItemSearchFilterBinding

class SelectedFilterRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<SelectedFilterRecyclerViewHolder>() {
    var data = listOf<SelectedFilterViewModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFilterRecyclerViewHolder {
        val binding= RvItemSearchFilterBinding.inflate(LayoutInflater.from(context),parent,false)
        return SelectedFilterRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SelectedFilterRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class SelectedFilterRecyclerViewHolder(val binding: RvItemSearchFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SelectedFilterViewModel) {
        binding.selectedfiletervm = data

    }

}