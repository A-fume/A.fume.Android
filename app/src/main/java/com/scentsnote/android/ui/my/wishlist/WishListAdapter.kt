package com.scentsnote.android.ui.my.wishlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.ParcelableWishList
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.scentsnote.android.data.vo.response.ResponseMyPerfume
import com.scentsnote.android.databinding.RvItemMyWishlistBinding
import com.scentsnote.android.ui.note.NoteActivity


class WishListAdapter (): RecyclerView.Adapter<WishListRecyclerHolder>(){
    var data = mutableListOf<ResponseMyPerfume>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListRecyclerHolder {
        val binding=RvItemMyWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WishListRecyclerHolder(binding)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: WishListRecyclerHolder, position: Int) {
        holder.bind(data[position])
    }

    internal fun setWishListData(data: MutableList<ResponseMyPerfume>?){
        if(data!=null) this.data=data
        notifyDataSetChanged()
    }

}

class WishListRecyclerHolder(val binding: RvItemMyWishlistBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ResponseMyPerfume) {
        binding.wishlist = data
        binding.root.setOnClickListener {
            goToNotePageWithPerfumeInfo(it, data)
        }
    }

    fun goToNotePageWithPerfumeInfo(view: View, perfumeInfo: ResponseMyPerfume) {
        val intent = Intent(view.context, NoteActivity::class.java)
        val wishListPerfume = ParcelableWishList(perfumeInfo.perfumeIdx,perfumeInfo.reviewIdx,perfumeInfo.perfumeName,perfumeInfo.brandName,perfumeInfo.imageUrl)
        intent.putExtra("wishListPerfume", wishListPerfume)
        view.context.startActivity(intent)
    }

}