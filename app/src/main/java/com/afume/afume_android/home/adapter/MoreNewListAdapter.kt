package com.afume.afume_android.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.RvItemHomeMoreNewBinding

class MoreNewListAdapter(private val context: Context) : RecyclerView.Adapter<MoreNewListViewHolder>() {
    var data = mutableListOf<HomePerfumeListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreNewListViewHolder {
        val binding : RvItemHomeMoreNewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_more_new,
            parent,
            false
        )

        return MoreNewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreNewListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size
}

class MoreNewListViewHolder(val binding:RvItemHomeMoreNewBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : HomePerfumeListData){
        binding.item = item
    }
}