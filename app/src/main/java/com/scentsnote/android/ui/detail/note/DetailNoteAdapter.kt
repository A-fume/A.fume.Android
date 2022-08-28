package com.scentsnote.android.ui.detail.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.response.PerfumeDetailWithReviews
import com.scentsnote.android.databinding.RvItemDetailNoteBinding
import com.scentsnote.android.databinding.RvItemDetailNoteReportBinding
import com.scentsnote.android.ui.detail.PerfumeDetailViewModel
import com.scentsnote.android.util.CommonDialog
import com.scentsnote.android.util.ReportDialog

class DetailNoteAdapter(private val context: Context, private val vm: PerfumeDetailViewModel, private val fragmentManager: FragmentManager, val perfumeIdx: Int, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<PerfumeDetailWithReviews>()

    companion object{
        const val Default_TYPE = 0
        const val Report_TYPE = 1
    }

    fun replaceAll(array: ArrayList<PerfumeDetailWithReviews>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(data[position].isReported) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            Default_TYPE -> {
                val binding = RvItemDetailNoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DetailNoteViewHolder(
                    binding
                )
            }
            Report_TYPE -> {
                val binding = RvItemDetailNoteReportBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DetailNoteReportViewHolder(
                    binding
                )
            }
            else -> { throw RuntimeException("알 수 없는 뷰타입 에러")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            Default_TYPE -> {
                holder as DetailNoteViewHolder
                data[position].let{
                    holder.bind(it)
                }
            }
            Report_TYPE -> {
                holder as DetailNoteReportViewHolder
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class DetailNoteViewHolder(val binding: RvItemDetailNoteBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : PerfumeDetailWithReviews){
            binding.item = item

            binding.btnLike.setOnClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) createLoginDialog()
                else {
                    clickBtnLike(item.reviewIdx)
                }
            }

            binding.txtRvDetailNoteReport.setOnClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) createLoginDialog()
                else {
                    createReportDialog(item.reviewIdx)
                }
            }
        }

        private fun createLoginDialog() {
            val bundle = Bundle()
            bundle.putString("title", "login")
            val dialog: CommonDialog = CommonDialog().CustomDialogBuilder().getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }

        private fun createReportDialog(reviewIdx : Int){
            val bundle = Bundle()
            val dialog: ReportDialog = ReportDialog(vm).ReportDialogBuilder()
                .setBtnClickListener(object : ReportDialog.ReportDialogListener {
                    override fun onPositiveClicked() {
                        vm.reportReview(reviewIdx)
                        vm.getPerfumeInfoWithReview(perfumeIdx)
                    }
                    override fun onNegativeClicked() {
                    }
                })
                .getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }
    }

    inner class DetailNoteReportViewHolder(val binding: RvItemDetailNoteReportBinding):RecyclerView.ViewHolder(binding.root){

    }
}