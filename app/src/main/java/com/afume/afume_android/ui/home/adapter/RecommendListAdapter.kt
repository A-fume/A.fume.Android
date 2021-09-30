package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
import com.afume.afume_android.databinding.RvItemHomeRecommendBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity

class RecommendListAdapter(private val context: Context) :
    RecyclerView.Adapter<RecommendListViewHolder>() {
    var data = mutableListOf<RecommendPerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendListViewHolder {
        val binding: RvItemHomeRecommendBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_recommend,
            parent,
            false
        )

        return RecommendListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecommendListViewHolder, position: Int) {
        data[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setRecommendPerfume(data: MutableList<RecommendPerfumeItem>?) {
        if (data != null) this.data = data
        notifyDataSetChanged()
    }
}

class RecommendListViewHolder(val binding: RvItemHomeRecommendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecommendPerfumeItem?) {
        val adapter = HashTagAdapter()
        if (item != null) {
            if(item.keywordList!=null){
                adapter.data = item.keywordList
                binding.rvHomeHashTag.adapter = adapter

                binding.item = item
                binding.executePendingBindings()

                binding.root.setOnClickListener {
                    onClickPerfume(it, item.perfumeIdx)
                }
            }
        }
    }

    private fun onClickPerfume(view: View, perfumeIdx: Int) {
        val intent = Intent(view.context, PerfumeDetailActivity::class.java)
        intent.putExtra("perfumeIdx", perfumeIdx)
        view.context.startActivity(intent)
    }
}