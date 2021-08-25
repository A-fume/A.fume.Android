package com.afume.afume_android.ui.my.wishlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.ParcelableWishList
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.databinding.RvItemMyWishlistBinding
import com.afume.afume_android.ui.note.NoteActivity


class WishListAdapter (): RecyclerView.Adapter<WishListRecyclerHolder>(){
    var data = mutableListOf<PerfumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListRecyclerHolder {
        val binding=RvItemMyWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WishListRecyclerHolder(binding)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: WishListRecyclerHolder, position: Int) {
        holder.bind(data[position])
    }

    internal fun setWishListData(data: MutableList<PerfumeInfo>?){
        if(data!=null) this.data=data
        notifyDataSetChanged()
    }

}

class WishListRecyclerHolder(val binding: RvItemMyWishlistBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PerfumeInfo) {
        binding.wishlist = data
        binding.root.setOnClickListener {
            goToNotePageWithPerfumeInfo(it, data)
        }
    }

    fun goToNotePageWithPerfumeInfo(view: View, perfumeInfo: PerfumeInfo) {
        val intent = Intent(view.context, NoteActivity::class.java)
        val wishListPerfume = ParcelableWishList(perfumeInfo.perfumeIdx,0,perfumeInfo.name,perfumeInfo.brandName,perfumeInfo.imageUrl)
        intent.putExtra("wishListPerfume", wishListPerfume)
        view.context.startActivity(intent)
    }

}