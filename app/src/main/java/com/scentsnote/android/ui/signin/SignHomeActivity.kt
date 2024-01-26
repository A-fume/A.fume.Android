package com.scentsnote.android.ui.signin

import android.view.View
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.ActivitySignHomeBinding
import com.scentsnote.android.ui.signup.SignUpEmailActivity
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.utils.extension.startActivity

class SignHomeActivity : BaseActivity<ActivitySignHomeBinding>(R.layout.activity_sign_home) {

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("Splash", this::class.java.name)
    }

    fun onClickSignInBtn(view : View){
        this.startActivity(SignInActivity::class.java)
    }

    fun onClickSignUpBtn(view : View){
        this.startActivity(SignUpEmailActivity::class.java)
    }
}