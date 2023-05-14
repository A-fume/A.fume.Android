package com.scentsnote.android.ui.signup

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySignUpPasswordBinding
import com.scentsnote.android.viewmodel.signup.SignUpViewModel
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.setKeyboard
import com.scentsnote.android.utils.extension.startActivity

class SignUpPasswordActivity :
    BaseActivity<ActivitySignUpPasswordBinding>(R.layout.activity_sign_up_password) {
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = signUpViewModel
        }

        binding.edtSignUpPassword.requestFocus()

        passwordAnimation()
        checkNextButton()
        setKeyboard()
    }

    private fun passwordAnimation() {
        signUpViewModel.againPasswordForm.observe(this, Observer { isValidPassword ->
            isValidPassword?.let {
                if (isValidPassword) {
                    val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                    binding.clSignUpAgain.startAnimation(animation)
                }
            }
        })
    }

    private fun checkNextButton() {
        signUpViewModel.isValidPassword.observe(this, Observer {
            signUpViewModel.checkPasswordNextBtn()
        })
        signUpViewModel.isValidAgain.observe(this, Observer {
            signUpViewModel.checkPasswordNextBtn()
        })
    }

    private fun setKeyboard() {
        signUpViewModel.passwordNextBtn.observe(this, Observer { passwordNextBtn ->
            if (passwordNextBtn) {
                this.setKeyboard(false, null)
            }
        })
    }

    fun onClickNextBtn(view: View) {
        this.startActivity(SignUpGenderActivity::class.java)

        signUpViewModel.addUserInfo("password")
    }

    fun onClickBackBtn(view: View) {
        finish()
    }
}