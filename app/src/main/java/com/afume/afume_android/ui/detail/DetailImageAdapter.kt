package com.afume.afume_android.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.PerfumeDetailData
import com.afume.afume_android.databinding.RvItemDetailImageBinding
import com.bumptech.glide.Glide

class DetailImageAdapter(val context: Context) : RecyclerView.Adapter<DetailImageViewHolder>() {
    var data: List<String>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding : RvItemDetailImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_image,
            parent,
            false
        )

        return DetailImageViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        if (data.isNullOrEmpty()) return
        data!![position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return if(data.isNullOrEmpty()) 0
        else data!!.size
    }
}

class DetailImageViewHolder(val binding: RvItemDetailImageBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
    fun bind(image : String){
        Glide.with(context)
            .load(image)
            .into(binding.imageView2)
    }
}