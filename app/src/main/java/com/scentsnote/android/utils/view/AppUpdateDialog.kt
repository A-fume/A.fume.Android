package com.scentsnote.android.utils.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.R
import com.scentsnote.android.databinding.DialogAppUpdateBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setDrawable
import com.scentsnote.android.utils.extension.setHeight

class AppUpdateDialog: DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogAppUpdateBinding
    var listener: AppUpdateDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAppUpdateBinding.inflate(inflater, container, false)
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

    private fun processBundle(binding: DialogAppUpdateBinding) {
        val bundle = arguments
        when (bundle?.getString("title", "")) {
            "update" -> {
                setContents(getString(R.string.dialog_app_update), "업데이트")
            }
            "error" -> {
                setContents(getString(R.string.dialog_network_error), "확인")
            }
            "rooting" -> {
                setContents(getString(R.string.dialog_rooting), "확인")
            }
        }
        binding.btnConfirm.setOnSafeClickListener {
            dismiss()
            listener?.onPositiveClicked()
        }
    }

    private fun setContents(contents: String, confirm: String){
        binding.txtContents.text = contents
        binding.btnConfirm.text = confirm
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.onNegativeClicked()
    }

    inner class AppUpdateDialogBuilder {

        private val dialog = AppUpdateDialog()

        fun setBtnClickListener(listener: AppUpdateDialogListener): AppUpdateDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): AppUpdateDialog {
            return dialog
        }
    }

    interface AppUpdateDialogListener{
        fun onPositiveClicked()
        fun onNegativeClicked()
    }
}