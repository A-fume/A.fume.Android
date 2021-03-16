package com.afume.afume_android.ui.signup

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpEmailBinding
import com.afume.afume_android.util.startActivity

class SignUpEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpEmailBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_email)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

        binding.edtSignUpEmail.requestFocus()

        nickAnimation()
        checkNextButton()
    }

    private fun nickAnimation(){
        signUpViewModel.nickForm.observe(this, Observer { isValidNick->
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
        signUpViewModel.isValidEmail.observe(this, Observer {
            signUpViewModel.checkNextBtn()
        })
        signUpViewModel.isValidNick.observe(this, Observer{
            signUpViewModel.checkNextBtn()
        })
    }

    fun onClickNextBtn(view : View){
        this.startActivity(SignUpPasswordActivity::class.java)

        signUpViewModel.addUserInfo("email")
    }

    fun onClickBackBtn(view : View){
        finish()
    }
}
