package com.afume.afume_android.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpEmailBinding

class SignUpEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpEmailBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_email)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

        binding.edtSignUpEmail.requestFocus()

        startAnimation()
        checkNextButton()
    }

    private fun startAnimation(){
        signUpViewModel.nick.observe(this, Observer { isValidNick->
            isValidNick?.let {
                if(isValidNick){
                    val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                    binding.clSignUpNick.startAnimation(animation)

                    binding.edtSignUpNick.requestFocus()
                }
            }
        })
    }

    private fun checkNextButton(){
        signUpViewModel.isValidEmailImage.observe(this, {isValidEmailImage->
            isValidEmailImage?.let {
                if(isValidEmailImage){
                    signUpViewModel.checkNextBtn()
                }
            }
        })
        signUpViewModel.isValidNickImage.observe(this, {isValidNickImage->
            isValidNickImage?.let {
                if(isValidNickImage){
                    signUpViewModel.checkNextBtn()
                }
            }
        })
    }

    fun onClickNextBtn(view: View){
        val passwordIntent = Intent(this,SignUpPasswordActivity::class.java)

        startActivity(passwordIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}