package com.scents.note.ui.signin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scents.note.R
import com.scents.note.databinding.ActivitySignHomeBinding
import com.scents.note.ui.signup.SignUpEmailActivity
import com.scents.note.util.startActivity

class SignHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_home)
        binding.lifecycleOwner = this
    }

    fun onClickSignInBtn(view : View){
        this.startActivity(SignInActivity::class.java)
    }

    fun onClickSignUpBtn(view : View){
        this.startActivity(SignUpEmailActivity::class.java)
    }
}