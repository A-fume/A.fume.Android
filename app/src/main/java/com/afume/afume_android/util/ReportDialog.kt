package com.afume.afume_android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.databinding.DialogReviewReportBinding

class ReportDialog : DialogFragment(), View.OnClickListener {
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

        private val dialog = ReportDialog()

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