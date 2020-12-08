package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.RvItemHomeNewBinding

class NewListAdapter(private val context: Context) : RecyclerView.Adapter<NewListViewHolder>() {
    var data = mutableListOf<HomePerfumeListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewListViewHolder {
        val binding : RvItemHomeNewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_new,
            parent,
            false
        )

        return NewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size
}

class NewListViewHolder(val binding: RvItemHomeNewBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: HomePerfumeListData){
        binding.item = item
    }
}