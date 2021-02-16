package com.afume.afume_android.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpPasswordBinding
import com.afume.afume_android.util.CheckTextWatcher

class SignUpPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpPasswordBinding
    private val signUpViewModel : SignUpViewModel by viewModels()
    var checkAni = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_password)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

        passwordAnimation()
        checkNextButton()

        binding.edtSignUpPassword.requestFocus()
    }

    private fun passwordAnimation(){
        signUpViewModel.againPassword.observe(this, Observer { isValidPassword->
            isValidPassword?.let {
                if(isValidPassword){
                    val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                    binding.clSignUpAgain.startAnimation(animation)

                    binding.clSignUpAgain.requestFocus()
                }
            }
        })
    }

    private fun checkNextButton(){
        signUpViewModel.isValidPasswordImage.observe(this, Observer{isValidPasswordImage->
            isValidPasswordImage?.let {
                if(!isValidPasswordImage){
                    signUpViewModel.checkNextBtn()
                }
            }
        })
        signUpViewModel.isValidAgainImage.observe(this, Observer{isValidAgainImage->
            isValidAgainImage?.let {
                if(!isValidAgainImage){
                    signUpViewModel.checkNextBtn()
                }
            }
        })
    }

    fun onClickNextBtn(view: View){
        val genderIntent = Intent(this,SignUpGenderActivity::class.java)

        startActivity(genderIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}