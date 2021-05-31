package com.afume.afume_android.ui.detail.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.databinding.RvItemDetailKeywordBinding

class DetailKeywordAdapter : RecyclerView.Adapter<DetailKeywordAdapter.DetailKeywordViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailKeywordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_detail_keyword, parent, false)
        return DetailKeywordViewHolder(RvItemDetailKeywordBinding.bind(view))
    }

    override fun onBindViewHolder(holder: DetailKeywordViewHolder, position: Int) {
        holder.binding.apply {
            rvData = data[position]
            executePendingBindings()
        }

    }

    class DetailKeywordViewHolder(
        val binding: RvItemDetailKeywordBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

//class DetailKeywordViewHolder(
//    val binding: RvItemDetailKeywordBinding
//) : RecyclerView.ViewHolder(binding.root) {
//
//    var keywordData: ResponseKeyword? = null
//
//    fun bind(data: ResponseKeyword) {
//        this.keywordData = data
//        binding.itemFragFriendKnowCivProfile.glide(keywordData!!.profileImg, requestManager)
//    }
//}