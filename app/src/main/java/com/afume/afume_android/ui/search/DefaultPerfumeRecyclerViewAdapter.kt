package com.afume.afume_android.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.databinding.RvItemDefaultPerfumeBinding


class DefaultPerfumeRecyclerViewAdapter():RecyclerView.Adapter<DefaultPerfumeRecyclerViewHolder>(){
    var data = listOf<PerfumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultPerfumeRecyclerViewHolder {
        val binding=RvItemDefaultPerfumeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DefaultPerfumeRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: DefaultPerfumeRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    internal fun setData(list: MutableList<PerfumeInfo>){
        this.data = list
        notifyDataSetChanged()
    }

}


class DefaultPerfumeRecyclerViewHolder(val binding: RvItemDefaultPerfumeBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data: PerfumeInfo){
        binding.perfume=data
        binding.btnHeart.setOnClickListener {
            // 좋아요 누르면 로그인 하게 유도
            it.isSelected = ! data.isLiked

        }
    }
}