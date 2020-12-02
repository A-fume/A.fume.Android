package com.afume.afume_android.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.databinding.RvItemFilterFlexboxBinding


class FlexboxRecyclerViewAdapter (private val context: Context):RecyclerView.Adapter<FlexboxRecyclerViewHolder>(){
    var data = listOf<IncenseSeriesViewModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlexboxRecyclerViewHolder {
        val binding= RvItemFilterFlexboxBinding.inflate(LayoutInflater.from(context),parent,false)
        return FlexboxRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int=data.size

    override fun onBindViewHolder(holder: FlexboxRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

}
class FlexboxRecyclerViewHolder(val binding:RvItemFilterFlexboxBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data:IncenseSeriesViewModel){
        binding.seriesViewModel=data
    }
}