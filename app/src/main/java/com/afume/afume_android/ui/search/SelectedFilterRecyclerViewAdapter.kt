package com.afume.afume_android.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.SendFilter
import com.afume.afume_android.databinding.RvItemSearchFilterBinding

class SelectedFilterRecyclerViewAdapter(val search:()->Unit,val btnCancel:(FilterInfoP?)->Unit) :
    RecyclerView.Adapter<SelectedFilterRecyclerViewAdapter.SelectedFilterRecyclerViewHolder>() {

    var sendFilter = SendFilter(mutableListOf<FilterInfoP>(), mutableMapOf())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFilterRecyclerViewHolder {
        val binding= RvItemSearchFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectedFilterRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sendFilter.filterInfoPList?.size ?:0
    }

    override fun onBindViewHolder(holder: SelectedFilterRecyclerViewHolder, position: Int) {
        holder.bind(sendFilter.filterInfoPList?.get(position))
    }

    internal fun setData(filter:SendFilter){
        this.sendFilter=filter
        notifyDataSetChanged()
        Log.d("send Filter DATA list",sendFilter.toString())
    }

    inner class SelectedFilterRecyclerViewHolder(val binding: RvItemSearchFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilterInfoP?) {
            binding.filter = data
            binding.btnCancel.setOnClickListener {
                btnCancel(data)
                notifyDataSetChanged()
                search()
            }

        }

    }
}

