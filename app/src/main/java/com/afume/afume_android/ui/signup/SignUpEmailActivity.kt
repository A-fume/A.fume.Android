package com.afume.afume_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpEmailBinding
import com.afume.afume_android.util.CheckTextWatcher

class SignUpEmailActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpEmailBinding
    var checkAni = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_email)
        binding.lifecycleOwner = this

        checkEmailInput()
        checkNicknameInput()

    }

    private fun checkEmailInput(){
        binding.txtSignUpCheckEmail.visibility = View.INVISIBLE
        binding.imgSignUpCheckEmail.visibility = View.INVISIBLE

        binding.edtSignUpEmail.addTextChangedListener(CheckTextWatcher{
            Handler().postDelayed({ checkEmail() }, 1500L)
        })
    }

    private fun checkEmail() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtSignUpEmail.text).matches()) {
            binding.txtSignUpCheckEmail.visibility = View.INVISIBLE
            binding.imgSignUpCheckEmail.visibility = View.VISIBLE

            Handler().postDelayed({ startAnimation() }, 1000L)

        } else {
            binding.txtSignUpCheckEmail.visibility = View.VISIBLE
            binding.imgSignUpCheckEmail.visibility = View.INVISIBLE
        }

        checkNextButton()
    }

    private fun checkNicknameInput(){
        binding.imgSignUpCheckNick.visibility = View.INVISIBLE

        binding.edtSignUpNick.addTextChangedListener(CheckTextWatcher{checkNickname()})
    }

    private fun checkNickname() {
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
        binding.clSignUpEmailNext.setOnClickListener {
            Toast.makeText(this, "다음", Toast.LENGTH_SHORT).show()
        }
    }
}