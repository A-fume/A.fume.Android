package com.scentsnote.android.ui.detail.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.databinding.RvItemDetailPriceBinding

class PriceRvAdapter : RecyclerView.Adapter<PriceRvViewHolder>() {

    var viewType: Int = 0
    var data = mutableListOf<String>()

    fun replaceAll(array: ArrayList<String>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = viewType

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceRvViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_detail_price, parent, false)
        return PriceRvViewHolder(RvItemDetailPriceBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PriceRvViewHolder, position: Int) {
        data[position].let{
            holder.onBind(it)
        }
    }
}

class PriceRvViewHolder(val binding: RvItemDetailPriceBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(price: String){
        binding.price = price
        binding.executePendingBindings()
    }
}