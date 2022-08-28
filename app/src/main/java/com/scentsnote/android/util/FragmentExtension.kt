package com.scentsnote.android.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Fragment.refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
    var ft: FragmentTransaction = fragmentManager.beginTransaction()

    ft.detach(fragment).attach(fragment).commit()
}