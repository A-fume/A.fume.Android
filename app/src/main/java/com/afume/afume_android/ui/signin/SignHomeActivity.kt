package com.afume.afume_android.ui.signin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignHomeBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.signup.SignUpEmailActivity
import com.afume.afume_android.util.startActivity
import com.afume.afume_android.util.startActivityWithFinish
import com.afume.afume_android.util.toastLong

class SignHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_home)
        binding.lifecycleOwner = this

        checkLoginInfo()
    }

    private fun checkLoginInfo(){
        if(AfumeApplication.prefManager.accessToken.isNotEmpty()){
            this.startActivityWithFinish(MainActivity::class.java)
            this.toastLong("자동로그인 되었습니다.")
        }
    }

    fun onClickSignInBtn(view : View){
        this.startActivity(SignInActivity::class.java)
    }

    fun onClickSignUpBtn(view : View){
        this.startActivity(SignUpEmailActivity::class.java)
    }
}