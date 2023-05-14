package com.scentsnote.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.R
import com.scentsnote.android.databinding.DialogReviewReportBinding
import com.scentsnote.android.viewmodel.detail.PerfumeDetailViewModel

class ReportDialog(val vm : PerfumeDetailViewModel) : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogReviewReportBinding
    var listener: ReportDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogReviewReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        this.setDrawable()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        this.setHeight()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        processBundle(binding)
    }

    private fun processBundle(binding: DialogReviewReportBinding) {
        binding.rgReviewReport.setOnCheckedChangeListener { radioGroup, checkedId ->
            binding.btnReportDialogYes.isEnabled = true
            when(checkedId){
                R.id.rb_1_ad -> vm.setReportTxt("광고, 홍보")
                R.id.rb_2_swear_word -> vm.setReportTxt("욕설, 음란어 사용")
                R.id.rb_3_typo -> vm.setReportTxt("과도한 오타, 반복적 사용")
                R.id.rb_4_personal_info -> vm.setReportTxt("개인정보 노출")
                R.id.rb_5_irrelevant -> vm.setReportTxt("제품과 무관한 내용")
                R.id.rb_6_cyberlivel -> vm.setReportTxt("명예 훼손, 저작권 침해")
                R.id.rb_7_etc -> vm.setReportTxt("기타")
            }
        }

        binding.btnReportDialogYes.setOnClickListener {
            dismiss()
            listener?.onPositiveClicked()
        }

        binding.btnReportDialogNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    inner class ReportDialogBuilder {

        private val dialog = ReportDialog(vm)

        fun setBtnClickListener(listener: ReportDialogListener): ReportDialog.ReportDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): ReportDialog {
            return dialog
        }
    }

    interface ReportDialogListener{
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

}