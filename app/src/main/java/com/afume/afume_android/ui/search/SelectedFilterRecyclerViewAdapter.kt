package com.afume.afume_android.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.SendFilter
import com.afume.afume_android.databinding.RvItemSearchFilterBinding

class SelectedFilterRecyclerViewAdapter(val search:()->Unit) :
    RecyclerView.Adapter<SelectedFilterRecyclerViewAdapter.SelectedFilterRecyclerViewHolder>() {
    var filterList = mutableListOf<FilterInfoP>()
    var sendFilter = SendFilter(mutableListOf<FilterInfoP>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFilterRecyclerViewHolder {
        val binding= RvItemSearchFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectedFilterRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: SelectedFilterRecyclerViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    internal fun setData(list: MutableList<FilterInfoP>){
        this.filterList=list
        notifyDataSetChanged()
        Log.e("set Filter list",filterList.toString())
        filterList.forEach{
            Log.e("type",it.type.toString())
            sendFilter.filterInfoPList?.add(it)

        }
        Log.e("send Filter DATA list",sendFilter.toString())
    }

    inner class SelectedFilterRecyclerViewHolder(val binding: RvItemSearchFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilterInfoP) {
            binding.filter = data
            binding.btnCancel.setOnClickListener {
//                when(data.type){
//                    1->sendFilter.ingredientList?.remove(data)
//                    2->sendFilter.brandList?.remove(data)
//                    3->sendFilter.keywordList?.remove(data)
//                }
                sendFilter.filterInfoPList?.remove(data)
                filterList.remove(data)
                notifyDataSetChanged()

                Log.e("set Filter DATA list",sendFilter.toString())

                search()
                //TODO 업어진 필터 제외 다시 통신
            }

        }

    }
}

