package com.scentsnote.android.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.scentsnote.android.databinding.RvItemDefaultPerfumeBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.ui.detail.PerfumeDetailActivity.Companion.INTENT_EXTRA_PERFUME_IDX
import com.scentsnote.android.utils.createDialog
import com.scentsnote.android.utils.extension.setOnSafeClickListener


class DefaultPerfumeRecyclerViewAdapter(
    val context: Context,
    val fragmentManager: FragmentManager,
    val clickBtnHeart: (Int) -> Unit
) : ListAdapter<PerfumeInfo, DefaultPerfumeRecyclerViewAdapter.DefaultPerfumeRecyclerViewHolder>(
    PerfumeInfo.diffUtil
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultPerfumeRecyclerViewHolder {
        val binding =
            RvItemDefaultPerfumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultPerfumeRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: DefaultPerfumeRecyclerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class DefaultPerfumeRecyclerViewHolder(val binding: RvItemDefaultPerfumeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PerfumeInfo) {
            binding.perfume = data

            binding.root.setOnSafeClickListener {
                goToPerfumeDetailsWithPerfumeIdx(it, data.perfumeIdx)
            }

            binding.btnHeart.setOnSafeClickListener {
                // 좋아요 누르면 로그인 하게 유도
                if (!ScentsNoteApplication.prefManager.haveToken()) {
                    context.createDialog(fragmentManager, "login")
                } else {
                    clickBtnHeart(data.perfumeIdx)
                    it.isSelected = !it.isSelected
                }
            }
        }

        private fun goToPerfumeDetailsWithPerfumeIdx(view: View, perfumeIdx: Int) {
            val intent = Intent(view.context, PerfumeDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PERFUME_IDX, perfumeIdx)
            view.context.startActivity(intent)
        }
    }
}