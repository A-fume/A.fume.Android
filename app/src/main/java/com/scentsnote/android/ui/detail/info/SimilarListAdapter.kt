package com.scentsnote.android.ui.detail.info

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.response.RecommendPerfumeItem
import com.scentsnote.android.databinding.RvItemDetailSimilarBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.utils.extension.createDialog
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class SimilarListAdapter(private val context: Context, private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<SimilarListAdapter.SimilarListViewHolder>() {
    var data = mutableListOf<RecommendPerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarListViewHolder {
        val binding: RvItemDetailSimilarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_similar,
            parent,
            false
        )

        return SimilarListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: SimilarListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setSimilarPerfume(data : MutableList<RecommendPerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }

    inner class SimilarListViewHolder(val binding : RvItemDetailSimilarBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: RecommendPerfumeItem){
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