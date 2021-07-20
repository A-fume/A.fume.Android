package com.afume.afume_android.util

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.DialogCommonBinding
import com.afume.afume_android.ui.signin.SignHomeActivity

class CommonDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogCommonBinding
    var listener: CustomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCommonBinding.inflate(inflater, container, false)
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

    private fun processBundle(binding: DialogCommonBinding) {
        val bundle = arguments
        when (bundle?.getString("title", "")) {
            "login" -> {
                setContents(R.string.dialog_login, "취소", "로그인 하기")

                binding.btnCommonDialogYes.setOnClickListener {
                    val intent = Intent(context,SignHomeActivity::class.java)
                    startActivity(intent)
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
                    listener?.onPositiveClicked()
                }

                binding.btnCommonDialogNo.setOnClickListener {
                    dismiss()

                }
            }
            "save" -> {
                setContents(R.string.dialog_note_save, "취소", "저장하기")

                binding.btnCommonDialogYes.setOnClickListener {
                    dismiss()
                    listener?.onPositiveClicked()
                }

                binding.btnCommonDialogNo.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    private fun setContents(title: Int, cancel: String, confirm: String){
        binding.txtCommonDialogTitle.text = getString(title)
        binding.btnCommonDialogNo.text = cancel
        binding.btnCommonDialogYes.text = confirm
    }

    inner class CustomDialogBuilder {

        private val dialog = CommonDialog()

        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): CommonDialog {
            return dialog
        }
    }

    interface CustomDialogListener{
        fun onPositiveClicked()
    }

}