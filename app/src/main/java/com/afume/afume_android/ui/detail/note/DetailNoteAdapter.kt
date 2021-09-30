package com.afume.afume_android.ui.detail.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.PerfumeDetailWithReviews
import com.afume.afume_android.databinding.RvItemDetailNoteBinding
import com.afume.afume_android.util.CommonDialog

class DetailNoteAdapter(private val context: Context, private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<DetailNoteAdapter.DetailNoteViewHolder>() {
    var data = mutableListOf<PerfumeDetailWithReviews>()

    fun replaceAll(array: ArrayList<PerfumeDetailWithReviews>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailNoteViewHolder {
        val binding: RvItemDetailNoteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_note,
            parent,
            false
        )

        return DetailNoteViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: DetailNoteViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class DetailNoteViewHolder(val binding: RvItemDetailNoteBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : PerfumeDetailWithReviews){
            binding.item = item

            binding.btnLike.setOnClickListener {
                if (!AfumeApplication.prefManager.haveToken()) createDialog()
                else {
                    clickBtnLike(item.reviewIdx)
                }
            }
        }

        private fun createDialog() {
            val bundle = Bundle()
            bundle.putString("title", "login")
            val dialog: CommonDialog = CommonDialog().CustomDialogBuilder().getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }
    }
}