package com.afume.afume_android.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.databinding.RvItemDefaultPerfumeBinding


class DefaultPerfumeRecyclerViewAdapter(private val context:Context):RecyclerView.Adapter<DefaultPerfumeRecyclerViewHolder>(){
    var data = listOf<DefaultRecyclerViewPerfumeViewModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultPerfumeRecyclerViewHolder {
        val binding=RvItemDefaultPerfumeBinding.inflate(LayoutInflater.from(context),parent,false)
        return DefaultPerfumeRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: DefaultPerfumeRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

}


class DefaultPerfumeRecyclerViewHolder(val binding: RvItemDefaultPerfumeBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data: DefaultRecyclerViewPerfumeViewModel){
        binding.vmperfume=data
    }
}