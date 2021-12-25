package com.afume.afume_android.ui.setting

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityPasswordEditBinding
import com.afume.afume_android.util.closeKeyboard
import com.afume.afume_android.util.toastLong

class EditPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityPasswordEditBinding
    private val editViewModel : EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_password_edit)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        binding.edtEditPasswordCheck.requestFocus()

        observer()
        passwordAnimation()
        checkNextBtn()
        setKeyboard()
    }

    private fun observer(){
        editViewModel.showErrorToast.observe(this, Observer {
            this.toastLong("현재 비밀번호와 다른 비밀번호를 입력하세요.")
        })
    }

    private fun passwordAnimation(){
        editViewModel.newPasswordForm.observe(this, Observer { isValidPassword ->
            isValidPassword?.let {
                if(isValidPassword){
                    val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                    binding.clPasswordEditNew.startAnimation(animation)

                    binding.clPasswordEditNew.requestFocus()
                }
            }
        })
    }

    private fun checkNextBtn(){
        editViewModel.isValidNewPassword.observe(this, Observer{
            editViewModel.checkPasswordNextBtn()
        })
        editViewModel.isValidAgainPassword.observe(this, Observer{
            editViewModel.checkPasswordNextBtn()
        })
    }

    private fun setKeyboard(){
        editViewModel.passwordEditCompleteBtn.observe(this, Observer { passwordEditCompleteBtn ->
            if(passwordEditCompleteBtn){
                this.closeKeyboard()
            }
        })
    }

    fun onClickCompleteBtn(view: View){
        editViewModel.putPassword()

        editViewModel.isValidEditPassword.observe(this, Observer{ isValidEditPassword ->
            isValidEditPassword?.let{
                if(isValidEditPassword){
                    finish()
                }
            }
        })
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}