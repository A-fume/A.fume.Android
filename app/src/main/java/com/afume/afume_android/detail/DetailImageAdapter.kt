package com.afume.afume_android.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.PerfumeDetailData
import com.afume.afume_android.databinding.RvItemDetailImageBinding

class DetailImageAdapter : RecyclerView.Adapter<DetailImageViewHolder>() {
    var data = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding : RvItemDetailImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_image,
            parent,
            false
        )

        return DetailImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size
}

class DetailImageViewHolder(val binding: RvItemDetailImageBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(image : Int){
        binding.item = image
        binding.executePendingBindings()
    }
}