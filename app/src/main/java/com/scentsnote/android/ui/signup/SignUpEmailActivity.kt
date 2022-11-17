package com.scentsnote.android.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySignUpEmailBinding
import com.scentsnote.android.util.extension.setOnSafeClickListener
import com.scentsnote.android.util.view.BaseActivity
import com.scentsnote.android.util.setKeyboard
import com.scentsnote.android.util.startActivity

class SignUpEmailActivity : BaseActivity<ActivitySignUpEmailBinding>(R.layout.activity_sign_up_email) {
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = signUpViewModel
        }

        binding.edtSignUpEmail.requestFocus()

//        nickAnimation()
        initObserve()

        binding.txtPrivacyPolicy.setOnSafeClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
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

    private fun initObserve(){
        checkNextButton(signUpViewModel.isValidEmail)
        checkNextButton(signUpViewModel.isValidNick)
        checkNextButton(signUpViewModel.privacyBtn)

        setKeyboard(signUpViewModel.isValidNick)
        setKeyboard(signUpViewModel.emailNextBtn)
    }

    private fun checkNextButton(isValid: LiveData<Boolean>){
        isValid.observe(this, Observer {
            signUpViewModel.checkNextBtn()
        })
    }

    private fun setKeyboard(isValid: LiveData<Boolean>){
        isValid.observe(this, Observer {
            if(it){
                this.setKeyboard(false, null)
            }
        })
    }

    fun onClickNextBtn(view : View){
        this.startActivity(SignUpPasswordActivity::class.java)

        signUpViewModel.addUserInfo("email")
    }

    fun onClickBackBtn(view : View){
        finish()
    }

    fun onClickPrivacyBtn(view: View){
        this.startActivity(PrivacyPolicyActivity::class.java)
    }
}
