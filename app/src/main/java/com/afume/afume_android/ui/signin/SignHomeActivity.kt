package com.afume.afume_android.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignHomeBinding

class SignHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_home)
        binding.lifecycleOwner = this
    }

    fun onClickSignInBtn(view : View){
        val signInIntent = Intent(this,SignInActivity::class.java)

        startActivity(signInIntent)
    }

    fun onClickSignUpBtn(view : View){
//        val signUpIntent = Intent(this,SignUpActivity::class.java)
//
//        startActivity(signUpIntent)
    }
}