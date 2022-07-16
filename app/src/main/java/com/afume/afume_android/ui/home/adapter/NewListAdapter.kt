package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.HomePerfumeItem
import com.afume.afume_android.databinding.RvItemHomeNewBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.createDialog

class NewListAdapter(private val context: Context, private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<NewListAdapter.NewListViewHolder>() {
    var data = mutableListOf<HomePerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewListViewHolder {
        val binding : RvItemHomeNewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_new,
            parent,
            false
        )

        return NewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setNewPerfume(data : MutableList<HomePerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }

    inner class NewListViewHolder(val binding: RvItemHomeNewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomePerfumeItem){
            binding.item = item

            binding.root.setOnClickListener {
                onClickPerfume(it, item.perfumeIdx)
            }

            binding.btnLike.setOnClickListener {
                if (!AfumeApplication.prefManager.haveToken()) context.createDialog(fragmentManager, "login")
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