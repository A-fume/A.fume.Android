package com.afume.afume_android.ui.survey

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.databinding.RvItemSurveyCircleBinding
import com.afume.afume_android.databinding.RvItemSurveySeriesBinding

class CircleRecyclerViewAdapter(private val type:Int, val add:(Int)->Unit, val remove:(Int)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var perfumeData = mutableListOf<PerfumeInfo>()
    var seriesData= mutableListOf<SeriesInfo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            PERfUME_TYPE -> {
                val binding = RvItemSurveyCircleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CirclePerfumeViewHolder(binding, add, remove)
            }
            SERIES_TYPE -> {
                val binding = RvItemSurveySeriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CircleSeriesViewHolder(binding, add, remove)
            }
            else -> { throw RuntimeException("알 수 없는 뷰타입 에러")}
        }
    }


    override fun getItemCount(): Int {
        return when(type){
            PERfUME_TYPE -> perfumeData.size
            SERIES_TYPE -> seriesData.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(type){
            PERfUME_TYPE -> {
                holder as CirclePerfumeViewHolder
                holder.bind(perfumeData[position])
            }
            SERIES_TYPE -> {
                holder as CircleSeriesViewHolder
                holder.bind(seriesData[position])
            }
        }
    }

    internal fun setPerfumeData(data: MutableList<PerfumeInfo>?){
        if(data!=null) this.perfumeData=data
        notifyDataSetChanged()
    }

    internal fun setSeriesData(data: MutableList<SeriesInfo>?){
        if(data!=null) this.seriesData=data
        notifyDataSetChanged()
    }

    companion object{
        const val PERfUME_TYPE = 0
        const val SERIES_TYPE = 1
    }
}

class CirclePerfumeViewHolder(val binding:RvItemSurveyCircleBinding,val add:(Int)->Unit, val remove:(Int)->Unit):RecyclerView.ViewHolder(binding.root){
    fun bind(data:PerfumeInfo){

        binding.perfume=data
        binding.root.setOnClickListener {
            Log.e("adapter",data.isLiked.toString())
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

class CircleSeriesViewHolder(val binding:RvItemSurveySeriesBinding,val add:(Int)->Unit, val remove:(Int)->Unit):RecyclerView.ViewHolder(binding.root){
    fun bind(data:SeriesInfo){

        binding.series=data
        binding.root.setOnClickListener {
            Log.e("adapter",data.isLiked.toString())
            if(!data.isLiked) {
                binding.rvItemSurveyClick.visibility=View.VISIBLE
                data.isLiked=true
                add(data.seriesIdx)
            }
            else {
                binding.rvItemSurveyClick.visibility=View.INVISIBLE
                data.isLiked=false
                remove(data.seriesIdx)
            }
        }
    }

}
