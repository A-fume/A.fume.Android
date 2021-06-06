package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
import com.afume.afume_android.databinding.RvItemHomePopularBinding

class PopularListAdapter(private val context: Context) : RecyclerView.Adapter<PopularListViewHolder>() {
    var data = mutableListOf<RecommendPerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularListViewHolder {
        val binding: RvItemHomePopularBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_popular,
            parent,
            false
        )

        return PopularListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: PopularListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setCommonPerfume(data : MutableList<RecommendPerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }
}

class PopularListViewHolder(val binding : RvItemHomePopularBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: RecommendPerfumeItem){
        binding.item = item
        binding.executePendingBindings()
    }
}