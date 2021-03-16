package com.afume.afume_android.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpAgeBinding
import com.afume.afume_android.util.YearPickerDialog

class SignUpAgeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpAgeBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_age)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

    }

     fun showYearPicker(view: View){
         val dialog: DialogFragment = YearPickerDialog(binding)
         dialog.show(this.supportFragmentManager, dialog.tag)
    }

    fun onClickCompleteBtn(view: View){
        //this.startActivity(SurveyActivity::class.java)

        signUpViewModel.addUserInfo("age")
        // 로그인 성공했을때만 intent 넘어가게 처리!!!
        //signUpViewModel.postRegister()
        Log.d("명", AfumeApplication.prefManager.userNickname)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}