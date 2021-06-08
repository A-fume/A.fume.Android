package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.NewPerfumeItem
import com.afume.afume_android.databinding.RvItemHomeMoreNewBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity

class MoreNewListAdapter(private val context: Context) : RecyclerView.Adapter<MoreNewListViewHolder>() {
    var data = mutableListOf<NewPerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreNewListViewHolder {
        val binding : RvItemHomeMoreNewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_more_new,
            parent,
            false
        )

        return MoreNewListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MoreNewListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setNewPerfume(data : MutableList<NewPerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }
}

class MoreNewListViewHolder(val binding:RvItemHomeMoreNewBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : NewPerfumeItem){
        binding.item = item

        binding.root.setOnClickListener {
            onClickPerfume(it, item.perfumeIdx)
        }
    }

    private fun onClickPerfume(view: View, perfumeIdx: Int){
        val intent = Intent(view.context, PerfumeDetailActivity::class.java)
        intent.putExtra("perfumeIdx", perfumeIdx)
        view.context.startActivity(intent)
    }
}