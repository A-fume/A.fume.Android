package com.afume.afume_android.ui.detail.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.databinding.RvPerfumeDetailPriceBinding

class PriceRvAdapter : RecyclerView.Adapter<PriceRvAdapter.PriceRvViewHolder>() {

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
            .inflate(R.layout.rv_perfume_detail_price, parent, false)
        return PriceRvViewHolder(RvPerfumeDetailPriceBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PriceRvViewHolder, position: Int) {
        holder.binding.apply {
            price = data[position]
            executePendingBindings()
        }

    }

    class PriceRvViewHolder(
        val binding: RvPerfumeDetailPriceBinding
    ) : RecyclerView.ViewHolder(binding.root)
}