package com.afume.afume_android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.DialogCommonBinding

class CommonDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogCommonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCommonBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        processBundle(binding)

        this.setDrawable()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        this.setHeight()
    }

    private fun processBundle(binding: DialogCommonBinding) {
        val bundle = arguments
        when (bundle?.getString("title", "")) {
            "login" -> {
                setContents(R.string.dialog_login, "취소", "로그인 하기")

                binding.btnCommonDialogYes.setOnClickListener {
                    dismiss()
                }

                binding.btnCommonDialogNo.setOnClickListener {
                    dismiss()
                }
            }
            "delete" -> {
                setContents(R.string.dialog_note_delete, "취소", "완료")

                binding.btnCommonDialogYes.setOnClickListener {
                    dismiss()
                }

                binding.btnCommonDialogNo.setOnClickListener {
                    dismiss()
                }
            }
            "save" -> {
                setContents(R.string.dialog_note_save, "취소", "저장하기")

                binding.btnCommonDialogYes.setOnClickListener {
                    dismiss()
                }

                binding.btnCommonDialogNo.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    fun getInstance(): CommonDialog {
        return CommonDialog()
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    private fun setContents(title: Int, cancel: String, confirm: String){
        binding.txtCommonDialogTitle.text = getString(title)
        binding.btnCommonDialogNo.text = cancel
        binding.btnCommonDialogYes.text = confirm
    }
}