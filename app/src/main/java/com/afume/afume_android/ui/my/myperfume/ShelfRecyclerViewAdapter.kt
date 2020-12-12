package com.afume.afume_android.ui.my.myperfume

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.RvItemMyPerfuemBgShelfBinding

class ShelfRecyclerViewAdapter(val count: Int) : RecyclerView.Adapter<ShelfRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfRecyclerViewHolder {
        val binding = RvItemMyPerfuemBgShelfBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShelfRecyclerViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return if (count % 3 == 0) count / 3
        else count/3+1
    }

    override fun onBindViewHolder(holder: ShelfRecyclerViewHolder, position: Int) {
        holder.bind()
    }

}

class ShelfRecyclerViewHolder(val binding: RvItemMyPerfuemBgShelfBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.shelf = R.drawable.bg_shelf
    }
}