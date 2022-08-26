package com.scents.note.ui.signin

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.scents.note.R
import com.scents.note.databinding.ActivitySignInBinding
import com.scents.note.ui.MainActivity
import com.scents.note.ui.signup.SignUpEmailActivity
import com.scents.note.util.startActivity
import com.scents.note.util.startActivityWithFinish

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    private val signInViewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        binding.lifecycleOwner = this
        binding.viewModel = signInViewModel

        binding.edtSignInEmail.requestFocus()

        signInViewModel.checkRegisterInfo()
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
        signInViewModel.postLoginInfo()

        signInViewModel.isValidLogin.observe(this, Observer { isValidLogin ->
            isValidLogin?.let {
                if(isValidLogin){
                    this.startActivityWithFinish(MainActivity::class.java)
                }
            }
        })
    }

    fun onClickSignUpBtn(view: View){
        this.startActivity(SignUpEmailActivity::class.java)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}