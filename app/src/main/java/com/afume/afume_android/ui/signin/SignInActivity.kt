package com.afume.afume_android.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignInBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.signup.SignUpEmailActivity

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    private val signInViewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        binding.lifecycleOwner = this
        binding.viewModel = signInViewModel

        checkNextBtn()
    }

    private fun checkNextBtn(){
        signInViewModel.isValidEmail.observe(this, Observer {
            signInViewModel.checkLoginNextBtn()
        })
        signInViewModel.isValidPassword.observe(this, Observer {
            signInViewModel.checkLoginNextBtn()
        })
    }

    fun onClickSignInBtn(view: View){
        val homeIntent = Intent(this,MainActivity::class.java)

        startActivity(homeIntent)
    }

    fun onClickSignUpBtn(view: View){
        val signUpIntent = Intent(this,SignUpEmailActivity::class.java)

        startActivity(signUpIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}