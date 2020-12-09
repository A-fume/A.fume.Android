package com.afume.afume_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpEmailBinding
import com.afume.afume_android.util.CheckTextWatcher

class SignUpEmailActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_email)
        binding.lifecycleOwner = this

        checkChange()
    }

    private fun checkChange(){
        binding.txtSignUpCheckEmail.visibility = View.INVISIBLE
        binding.imgSignUpCheckEmail.visibility = View.INVISIBLE

        binding.edtSignUpEmail.addTextChangedListener(CheckTextWatcher{checkEmail()})
    }

    private fun checkEmail() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtSignUpEmail.text).matches()) {
            binding.txtSignUpCheckEmail.visibility = View.INVISIBLE
            binding.imgSignUpCheckEmail.visibility = View.VISIBLE
        } else {
            binding.txtSignUpCheckEmail.visibility = View.VISIBLE
            binding.imgSignUpCheckEmail.visibility = View.INVISIBLE
        }

    }

}