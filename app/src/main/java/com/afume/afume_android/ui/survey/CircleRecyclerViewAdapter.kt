package com.afume.afume_android.ui.survey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.databinding.RvItemSurveyCircleBinding

class CircleRecyclerViewAdapter(val type:String?, val add:(Int)->Unit, val remove:(Int)->Unit):RecyclerView.Adapter<CircleRecyclerViewAdapter.CircleRecyclerViewHolder>(){
    var data = listOf<PerfumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleRecyclerViewHolder {
        val binding=RvItemSurveyCircleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CircleRecyclerViewHolder(binding,type,parent.context)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: CircleRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CircleRecyclerViewHolder(val binding:RvItemSurveyCircleBinding,val type:String?,val ctx:Context):RecyclerView.ViewHolder(binding.root){
        fun bind(data:PerfumeInfo){
            if(type=="incense") binding.rvItemTxtSurveyName.setTextColor(ContextCompat.getColor(ctx,R.color.primary_black))

            binding.perfume=data
            binding.root.setOnClickListener {
                if(!data.isLiked) {
                    binding.rvItemSurveyClick.visibility=View.VISIBLE
                    data.isLiked=true
                    add(data.perfumeIdx)
                }
                else {
                    binding.rvItemSurveyClick.visibility=View.INVISIBLE
                    data.isLiked=false
                    remove(data.perfumeIdx)
                }
            }
        }

    }
}
