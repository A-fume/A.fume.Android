package com.scents.note.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scents.note.R
import com.scents.note.databinding.ActivitySignUpGenderBinding
import com.scents.note.util.startActivity

class SignUpGenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpGenderBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_gender)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

    }

    fun onClickNextBtn(view: View){
        this.startActivity(SignUpAgeActivity::class.java)

        signUpViewModel.addUserInfo("gender")
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}