package com.scentsnote.android.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySignUpAgeBinding
import com.scentsnote.android.ui.survey.SurveyActivity
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.startActivityWithFinish
import com.scentsnote.android.utils.view.YearPickerDialog

class SignUpAgeActivity : BaseActivity<ActivitySignUpAgeBinding>(R.layout.activity_sign_up_age) {
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = signUpViewModel
        }
    }

     fun showYearPicker(view: View){
         val dialog: DialogFragment = YearPickerDialog(binding.btnSignUpYearPicker)
         dialog.show(this.supportFragmentManager, dialog.tag)
    }

    fun onClickCompleteBtn(view: View){
        signUpViewModel.addUserInfo("age")

        signUpViewModel.postRegister()

        signUpViewModel.isValidRegister.observe(this, Observer { isValidRegister ->
            isValidRegister?.let {
                if(isValidRegister){
                    this.startActivityWithFinish(SurveyActivity::class.java)
                }
            }
        })
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}