package com.scentsnote.android.util

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.scentsnote.android.util.view.CommonDialog

fun Context.createDialog(fragmentManager: FragmentManager, title: String) {
    val bundle = Bundle()
    bundle.putString("title", title)
    val dialog: CommonDialog = CommonDialog().CustomDialogBuilder().getInstance()
    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}

fun Context.createListenerDialog(fragmentManager: FragmentManager, title: String, positiveClicked:() -> Unit, negativeClicked:() -> Unit) {
    val bundle = Bundle()
    bundle.putString("title", title)
    val dialog: CommonDialog = CommonDialog().CustomDialogBuilder()
        .setBtnClickListener(object : CommonDialog.CustomDialogListener {
            override fun onPositiveClicked() {
                positiveClicked()
            }
            override fun onNegativeClicked() {
                negativeClicked()
            }
        })
        .getInstance()
    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}