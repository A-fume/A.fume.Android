package com.afume.afume_android.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpGenderBinding
import com.afume.afume_android.util.setSelectedGenderTxt
import com.afume.afume_android.util.startActivity

class SignUpGenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpGenderBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_gender)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

        setSelectedFont()
    }

    private fun setSelectedFont(){
        signUpViewModel.isCheckMan.observe(this, Observer { isCheckMan ->
            isCheckMan?.let{
                if(isCheckMan){
                    binding.txtSignUpGenderMan.setSelectedGenderTxt(true)
                    binding.txtSignUpGenderWoman.setSelectedGenderTxt(false)
                }
            }
        })
        signUpViewModel.isCheckWoman.observe(this, Observer { isCheckWoman ->
            isCheckWoman?.let{
                if(isCheckWoman){
                    binding.txtSignUpGenderMan.setSelectedGenderTxt(false)
                    binding.txtSignUpGenderWoman.setSelectedGenderTxt(true)
                }
            }
        })
    }

    fun onClickNextBtn(view: View){
        this.startActivity(SignUpAgeActivity::class.java)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}