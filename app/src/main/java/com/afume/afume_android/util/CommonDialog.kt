package com.afume.afume_android.util

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
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

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(
            Window.FEATURE_NO_TITLE
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.common_dialog_back)
        dialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

//        context?.dialogFragmentResize(this, 0.9f)

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

    fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {

            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
//            val y = (size.y * height).toInt()
            window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)

        } else {

            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
//            val y = (rect.height() * height).toInt()

            window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

}