package com.scents.note.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.scents.note.R
import com.scents.note.databinding.RvItemDetailImageBinding
import com.bumptech.glide.Glide

class DetailImageAdapter(val context: Context) : RecyclerView.Adapter<DetailImageViewHolder>() {
    var data = mutableListOf<String>()

    fun replaceAll(array: ArrayList<String>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

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
        Log.d("getPerfumeInfo null", data.isNullOrEmpty().toString())
        if (data.isNullOrEmpty()) return
        data!![position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        Log.d("getPerfumeInfo size", data?.size.toString())
        return if(data.isNullOrEmpty()) 0
        else data!!.size
    }
}

class DetailImageViewHolder(val binding: RvItemDetailImageBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
    fun bind(image : String){
        Log.d("getPerfumeInfo images", image)
        Glide.with(context)
            .load(image)
            .into(binding.imageView2)
    }
}