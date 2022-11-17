package com.scentsnote.android.util.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.databinding.DialogAppUpdateBinding
import com.scentsnote.android.util.extension.setOnSafeClickListener
import com.scentsnote.android.util.setDrawable
import com.scentsnote.android.util.setHeight

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
        binding.button7.setOnSafeClickListener {
            dismiss()
            listener?.onPositiveClicked()
        }
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