package com.afume.afume_android.ui.survey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvCircleData
import com.afume.afume_android.databinding.RvItemSurveyCircleBinding

class CircleRecyclerViewAdapter(val type:String?):RecyclerView.Adapter<CircleRecyclerViewAdapter.CircleRecyclerViewHolder>(){
    var data = listOf<RvCircleData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleRecyclerViewHolder {
        val binding=RvItemSurveyCircleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CircleRecyclerViewHolder(binding,type,parent.context)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: CircleRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CircleRecyclerViewHolder(val binding:RvItemSurveyCircleBinding,val type:String?,val ctx:Context):RecyclerView.ViewHolder(binding.root){
        fun bind(data:RvCircleData){
            if(type=="incense") binding.rvItemTxtSurveyName.setTextColor(ContextCompat.getColor(ctx,R.color.primary_black))
            binding.rvCircle=data
            binding.root.setOnClickListener {
                if(binding.rvItemSurveyClick.visibility==View.INVISIBLE) binding.rvItemSurveyClick.visibility=View.VISIBLE
                else binding.rvItemSurveyClick.visibility=View.INVISIBLE
            }
        }

    }
}
