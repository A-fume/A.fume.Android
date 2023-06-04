package com.scentsnote.android.utils.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.scentsnote.android.R

fun Fragment.refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
    var ft: FragmentTransaction = fragmentManager.beginTransaction()

    ft.detach(fragment).attach(fragment).commit()
}

internal fun Fragment.closeSelf() {
    parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
}

internal fun Fragment.closeSelfWithAnimation() {
    parentFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.slide_up,
            R.anim.slide_down,
            R.anim.slide_up,
            R.anim.slide_down
        ).remove(this).commitAllowingStateLoss()
}
