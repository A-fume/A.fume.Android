package com.afume.afume_android.ui.my.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.RvItemMyWishlistBinding


class WishListRecyclerViewAdapter (): RecyclerView.Adapter<WishListRecyclerHolder>(){
    var data = listOf<RvMyPerfumeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListRecyclerHolder {
        val binding=RvItemMyWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WishListRecyclerHolder(binding)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: WishListRecyclerHolder, position: Int) {
        holder.bind(data[position])
    }

}
class WishListRecyclerHolder (val binding: RvItemMyWishlistBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data:RvMyPerfumeData){
        binding.wishlist=data
    }

}