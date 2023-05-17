package com.scentsnote.android.ui.detail.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.response.PerfumeDetailWithReviews
import com.scentsnote.android.databinding.RvItemDetailNoteBinding
import com.scentsnote.android.databinding.RvItemDetailNoteReportBinding
import com.scentsnote.android.viewmodel.detail.PerfumeDetailViewModel
import com.scentsnote.android.util.LayoutedTextView.OnLayoutListener
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.view.CommonDialog
import com.scentsnote.android.utils.view.ReportDialog

class DetailNoteAdapter(
    private val vm: PerfumeDetailViewModel,
    private val fragmentManager: FragmentManager,
    private val perfumeIdx: Int,
    private val clickBtnLike: (Int) -> Unit
) : ListAdapter<PerfumeDetailWithReviews, RecyclerView.ViewHolder>(PerfumeDetailWithReviews.diffUtil) {

    private var isBlurType = true

    /** 시향노트 표시 종류 : 2가지 */
    companion object {
        const val DEFAULT_TYPE = 0 // 일반 리뷰
        const val REPORT_TYPE = 1 // 신고한 리뷰
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isReported) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            DEFAULT_TYPE -> {
                val binding = RvItemDetailNoteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return DetailNoteViewHolder(
                    parent.context, binding
                )
            }

            REPORT_TYPE -> {
                val binding = RvItemDetailNoteReportBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return DetailNoteReportViewHolder(
                    binding
                )
            }

            else -> {
                throw RuntimeException("알 수 없는 뷰타입 에러")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DEFAULT_TYPE -> {
                holder as DetailNoteViewHolder
                currentList[position].let {
                    holder.bind(it)
                }
            }

            REPORT_TYPE -> {
                holder as DetailNoteReportViewHolder
            }
        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class DetailNoteViewHolder(val context: Context, val binding: RvItemDetailNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PerfumeDetailWithReviews) {
            binding.item = item

            /** 좋아요 버튼 */
            binding.btnLike.setOnSafeClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) createLoginDialog()
                else {
                    clickBtnLike(item.reviewIdx)
                }
            }

            /** 신고 버튼 */
            binding.txtRvDetailNoteReport.setOnSafeClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) createLoginDialog()
                else {
                    createReportDialog(item.reviewIdx)
                }
            }

            /** 더보기 버튼 */
            binding.txtDetailsReviewContent.setOnLayoutListener(object : OnLayoutListener {
                override fun onLayouted(view: TextView?) {
                    val lineCount = view!!.lineCount
                    if (view.text.isNotEmpty()) {
                        setVisibilityMore(lineCount, context)
                    }
                }
            })

            binding.txtReviewMore.setOnSafeClickListener {
                if (binding.txtDetailsReviewContent.maxLines > 3) {
                    isBlurType = true
                    binding.txtDetailsReviewContent.maxLines = 3
                    binding.txtReviewMore.text = "더보기"
                    binding.txtReviewMore.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(context, R.drawable.icon_btn_down),
                        null
                    )
                } else {
                    isBlurType = false
                    binding.txtDetailsReviewContent.maxLines = Int.MAX_VALUE
                    binding.txtReviewMore.text = "접기"
                    binding.txtReviewMore.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(context, R.drawable.icon_btn_up), null
                    )
                }
            }

        }

        /** 로그인 유도 다이얼로그 : 좋아요, 신고 버튼의 경우 비로그인 상태로 클릭 시 로그인 유도 */
        private fun createLoginDialog() {
            val bundle = Bundle()
            bundle.putString("title", "login")
            val dialog: CommonDialog = CommonDialog().CustomDialogBuilder().getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }

        private fun createReportDialog(reviewIdx: Int) {
            val bundle = Bundle()
            val dialog: ReportDialog = ReportDialog(vm).ReportDialogBuilder()
                .setBtnClickListener(object : ReportDialog.ReportDialogListener {
                    override fun onPositiveClicked() {
                        vm.reportReview(reviewIdx)
                        vm.getPerfumeInfoWithReview(perfumeIdx)
                    }

                    override fun onNegativeClicked() {
                    }
                }).getInstance()
            dialog.arguments = bundle
            dialog.show(fragmentManager, dialog.tag)
        }

        /** 더보기 버튼 : 시향 노트 3줄 이상일 경우에는 더보기 버튼 표시 */
        fun setVisibilityMore(lineCount: Int, context: Context) {
            if (lineCount > 3) {
                binding.run {
                    txtReviewMore.visibility = View.VISIBLE
                    clReviewMore.background = if (isBlurType) {
                        ContextCompat.getDrawable(
                            context, R.drawable.background_btn_details_more
                        )
                    } else {
                        ContextCompat.getDrawable(context, R.color.transparent)
                    }
                }
            } else {
                binding.run {
                    txtReviewMore.visibility = View.GONE
                    clReviewMore.background =
                        ContextCompat.getDrawable(context, R.color.transparent)
                }
            }
        }
    }

    inner class DetailNoteReportViewHolder(val binding: RvItemDetailNoteReportBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}