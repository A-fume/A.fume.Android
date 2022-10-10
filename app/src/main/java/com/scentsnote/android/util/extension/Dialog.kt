package com.scentsnote.android.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.R

fun DialogFragment.setDrawable(){
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window?.requestFeature(
        Window.FEATURE_NO_TITLE
    )
}

fun DialogFragment.setHeight(){
    val width = resources.getDimensionPixelSize(R.dimen.dialog_width)

    dialog?.window?.setBackgroundDrawableResource(R.drawable.common_dialog_back)
    dialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

//        context?.dialogFragmentResize(this, 0.9f)
}

//fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float) {
//    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//
//    if (Build.VERSION.SDK_INT < 30) {
//
//        val display = windowManager.defaultDisplay
//        val size = Point()
//
//        display.getSize(size)
//
//        val window = dialogFragment.dialog?.window
//
//        val x = (size.x * width).toInt()
////            val y = (size.y * height).toInt()
//        window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)
//
//    } else {
//
//        val rect = windowManager.currentWindowMetrics.bounds
//
//        val window = dialogFragment.dialog?.window
//
//        val x = (rect.width() * width).toInt()
////            val y = (rect.height() * height).toInt()
//
//        window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)
//    }
//}