package com.afume.afume_android.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpPasswordBinding
import com.afume.afume_android.util.CheckTextWatcher

class SignUpPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpPasswordBinding
    var checkAni = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_password)
        binding.lifecycleOwner = this

        checkPasswordInput()
        checkAgainInput()

        binding.edtSignUpPassword.requestFocus()
    }

    private fun checkPasswordInput(){
        binding.txtSignUpCheckPassword.visibility = View.INVISIBLE
        binding.imgSignUpCheckPassword.visibility = View.INVISIBLE

        binding.edtSignUpPassword.addTextChangedListener(CheckTextWatcher{
            Handler().postDelayed({ checkPasswordForm() }, 2000L)
        })
    }

    private fun checkPasswordForm(){
        if(binding.edtSignUpPassword.text.length > 3){
            binding.txtSignUpCheckPassword.visibility = View.INVISIBLE
            binding.imgSignUpCheckPassword.visibility = View.VISIBLE

            Handler().postDelayed({ startAnimation() }, 1000L)
        }else{
            binding.txtSignUpCheckPassword.visibility = View.VISIBLE
            binding.imgSignUpCheckPassword.visibility = View.INVISIBLE
        }

        checkAgainForm()
        checkNextButton()
    }

    private fun checkAgainInput(){
        binding.txtSignUpCheckAgain.visibility = View.INVISIBLE
        binding.imgSignUpCheckAgain.visibility = View.INVISIBLE

        binding.edtSignUpAgain.addTextChangedListener(CheckTextWatcher{checkAgainForm()})
    }

    private fun checkAgainForm(){
        if(binding.edtSignUpAgain.text.toString() == binding.edtSignUpPassword.text.toString()){
            binding.txtSignUpCheckAgain.visibility = View.INVISIBLE
            binding.imgSignUpCheckAgain.visibility = View.VISIBLE
        }else if(binding.edtSignUpAgain.text.isNotEmpty()){
            binding.txtSignUpCheckAgain.visibility = View.VISIBLE
            binding.imgSignUpCheckAgain.visibility = View.INVISIBLE
        }

        checkNextButton()
    }

    private fun checkNextButton(){
        binding.clSignUpPasswordNext.visibility = View.INVISIBLE

        if(binding.imgSignUpCheckPassword.visibility == View.VISIBLE && binding.imgSignUpCheckAgain.visibility == View.VISIBLE){
            binding.clSignUpPasswordNext.visibility = View.VISIBLE
        }else{
            binding.clSignUpPasswordNext.visibility = View.INVISIBLE
        }
    }

    private fun startAnimation(){
        if(checkAni){
            checkAni = false
            binding.clSignUpAgain.visibility = View.VISIBLE

            val animation = AnimationUtils.loadAnimation(this,R.anim.alpha_up)
            binding.clSignUpAgain.startAnimation(animation)

            binding.clSignUpAgain.requestFocus()
        }
    }

    fun onClickNextBtn(view: View){
        val genderIntent = Intent(this,SignUpGenderActivity::class.java)

        startActivity(genderIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}