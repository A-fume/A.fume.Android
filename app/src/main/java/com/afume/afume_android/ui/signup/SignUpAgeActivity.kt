package com.afume.afume_android.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpAgeBinding
import com.afume.afume_android.ui.survey.SurveyActivity
import com.afume.afume_android.util.YearPickerDialog

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
         val dialog: DialogFragment = YearPickerDialog(binding)
         dialog.show(this.supportFragmentManager, dialog.tag)
    }

    fun onClickCompleteBtn(view: View){
        val surveyIntent = Intent(this,SurveyActivity::class.java)

        startActivity(surveyIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}