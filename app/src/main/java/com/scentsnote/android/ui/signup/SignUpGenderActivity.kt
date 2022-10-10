package com.scentsnote.android.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySignUpGenderBinding
import com.scentsnote.android.util.view.BaseActivity
import com.scentsnote.android.util.startActivity

class SignUpGenderActivity : BaseActivity<ActivitySignUpGenderBinding>(R.layout.activity_sign_up_gender) {
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = signUpViewModel
        }
    }

    fun onClickNextBtn(view: View){
        this.startActivity(SignUpAgeActivity::class.java)

        signUpViewModel.addUserInfo("gender")
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}