package com.scentsnote.android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.HomePerfumeItem
import com.scentsnote.android.databinding.RvItemHomeRecentBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.utils.createDialog
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class RecentListAdapter(private val context: Context, private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<RecentListAdapter.RecentListViewHolder>() {
    var data = mutableListOf<HomePerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentListViewHolder {
        val binding : RvItemHomeRecentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_recent,
            parent,
            false
        )

        return RecentListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecentListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setRecentPerfume(data : MutableList<HomePerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }

    inner class RecentListViewHolder(val binding: RvItemHomeRecentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomePerfumeItem){
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnSafeClickListener {
                onClickPerfume(it, item.perfumeIdx)
            }

            binding.btnLike.setOnSafeClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) context.createDialog(fragmentManager, "login")
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
    }
}