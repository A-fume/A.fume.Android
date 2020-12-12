package com.afume.afume_android.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignInBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.signup.SignUpEmailActivity
import com.afume.afume_android.util.CheckTextWatcher

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        binding.lifecycleOwner = this

        checkForm()
    }

    private fun checkForm(){
        binding.edtSignInEmail.addTextChangedListener(CheckTextWatcher{checkSignInBtn()})
        binding.edtSingInPassword.addTextChangedListener(CheckTextWatcher{checkSignInBtn()})
    }

    private fun checkSignInBtn(){
        binding.btnSignIn.isEnabled =
            android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtSignInEmail.text).matches() &&
                binding.edtSingInPassword.text.length > 3
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