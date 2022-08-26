package com.scents.note.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.scents.note.R
import com.scents.note.databinding.ActivitySignUpAgeBinding
import com.scents.note.ui.survey.SurveyActivity
import com.scents.note.util.YearPickerDialog
import com.scents.note.util.startActivityWithFinish

class SignUpAgeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpAgeBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_age)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

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