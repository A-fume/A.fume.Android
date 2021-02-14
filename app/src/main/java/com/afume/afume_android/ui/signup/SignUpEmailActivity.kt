package com.afume.afume_android.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpEmailBinding
import com.afume.afume_android.util.CheckTextWatcher

class SignUpEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpEmailBinding
    private val signUpViewModel : SignUpViewModel by viewModels()
    var checkAni = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_email)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

        checkNicknameInput()

        binding.edtSignUpEmail.requestFocus()

    }

    private fun checkNicknameInput(){
        binding.imgSignUpCheckNick.visibility = View.INVISIBLE

        binding.edtSignUpNick.addTextChangedListener(CheckTextWatcher{checkNicknameForm()})
    }

    private fun checkNicknameForm() {
//        android.util.Patterns.matches("^[a-zA-Z0-9가-힣]*$",binding.edtSignUpNick

        if (binding.edtSignUpNick.text.isNotEmpty()) {
            binding.imgSignUpCheckNick.visibility = View.VISIBLE
        } else {
            binding.imgSignUpCheckNick.visibility = View.INVISIBLE
        }

        checkNextButton()
    }

    private fun checkNextButton(){
        binding.clSignUpEmailNext.visibility = View.INVISIBLE

        if(binding.imgSignUpCheckEmail.visibility == View.VISIBLE && binding.imgSignUpCheckNick.visibility == View.VISIBLE){
            binding.clSignUpEmailNext.visibility = View.VISIBLE
        }else{
            binding.clSignUpEmailNext.visibility = View.INVISIBLE
        }
    }

    private fun startAnimation(){
        if(checkAni){
            checkAni = false
            binding.clSignUpNick.visibility = View.VISIBLE

            val animation = AnimationUtils.loadAnimation(this,R.anim.alpha_up)
            binding.clSignUpNick.startAnimation(animation)

            binding.edtSignUpNick.requestFocus()
        }
    }

    fun onClickNextBtn(view: View){
        val passwordIntent = Intent(this,SignUpPasswordActivity::class.java)

        startActivity(passwordIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}