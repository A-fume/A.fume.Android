package com.scentsnote.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.databinding.RvItemHomeHashTagBinding

class HashTagAdapter : RecyclerView.Adapter<HashTagViewHolder>() {
    var data = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashTagViewHolder {
        val binding : RvItemHomeHashTagBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_hash_tag,
            parent,
            false
        )

        return HashTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HashTagViewHolder, position: Int) {
        data[position].let{
            holder.onBind(it)
        }
    }

    override fun getItemCount(): Int = data.size

}

class HashTagViewHolder(private val binding: RvItemHomeHashTagBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(tag: String){
        binding.tag = tag
        binding.executePendingBindings()
    }
}