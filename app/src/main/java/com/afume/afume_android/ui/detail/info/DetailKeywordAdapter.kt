package com.afume.afume_android.ui.detail.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.databinding.RvItemDetailKeywordBinding

class DetailKeywordAdapter : RecyclerView.Adapter<DetailKeywordViewHolder>() {
    var data = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailKeywordViewHolder {
        val binding : RvItemDetailKeywordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_keyword,
            parent,
            false
        )

        return DetailKeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailKeywordViewHolder, position: Int) {
        data[position].let{
            holder.onBind(it)
        }
    }

    override fun getItemCount() = data.size

    internal fun setKeyword(data: List<String>?) {
        if (data != null) this.data = data
        notifyDataSetChanged()
    }

}
class DetailKeywordViewHolder(val binding: RvItemDetailKeywordBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data: String){
        binding.rvData = data
        binding.executePendingBindings()
    }
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