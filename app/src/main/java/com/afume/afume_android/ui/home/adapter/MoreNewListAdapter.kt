package com.afume.afume_android.ui.home.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.HomePerfumeItem
import com.afume.afume_android.databinding.RvItemHomeMoreNewBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.CommonDialog

class MoreNewListAdapter(private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<MoreNewListAdapter.MoreNewListViewHolder>() {
    var data = mutableListOf<HomePerfumeItem>()

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

    internal fun setNewPerfume(data : MutableList<HomePerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }
    inner class MoreNewListViewHolder(val binding:RvItemHomeMoreNewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : HomePerfumeItem){
            binding.item = item

            binding.root.setOnClickListener {
                onClickPerfume(it, item.perfumeIdx)
            }

            binding.btnLike.setOnClickListener {
                if (!AfumeApplication.prefManager.haveToken()) createDialog()
                else {
                    clickBtnLike(item.perfumeIdx)
                }
            }
        }

        private fun onClickPerfume(view: View, perfumeIdx: Int){
            val intent = Intent(view.context, PerfumeDetailActivity::class.java)
            intent.putExtra("perfumeIdx", perfumeIdx)
            view.context.startActivity(intent)
        }

        private fun createDialog() {
            val bundle = Bundle()
            bundle.putString("title", "login")
            val dialog: CommonDialog = CommonDialog().getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }
    }
}