package com.afume.afume_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpGenderBinding

class SignUpGenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpGenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_gender)
        binding.lifecycleOwner = this

    }

    fun onClickManBtn(view: View){
        binding.imgSignUpGenderMan.isChecked = true
        binding.imgSignUpGenderWoman.isChecked = false

        setStyle(true)

        checkNextButton()
    }

    fun onClickWomanBtn(view: View){
        binding.imgSignUpGenderMan.isChecked = false
        binding.imgSignUpGenderWoman.isChecked = true

        setStyle(false)

        checkNextButton()
    }

    private fun setStyle(boolean: Boolean){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (boolean) {
                binding.txtSignUpGenderMan.setTextColor(this.resources.getColor(R.color.primary_black))
                binding.txtSignUpGenderMan.typeface = this.resources.getFont(R.font.nanummyeongjo_extrabold)

                binding.txtSignUpGenderWoman.setTextColor(this.resources.getColor(R.color.gray_cd))
                binding.txtSignUpGenderWoman.typeface = this.resources.getFont(R.font.nanummyeongjo)
            } else {
                binding.txtSignUpGenderMan.setTextColor(this.resources.getColor(R.color.gray_cd))
                binding.txtSignUpGenderMan.typeface = this.resources.getFont(R.font.nanummyeongjo)

                binding.txtSignUpGenderWoman.setTextColor(this.resources.getColor(R.color.primary_black))
                binding.txtSignUpGenderWoman.typeface = this.resources.getFont(R.font.nanummyeongjo_extrabold)
            }
        }
    }

    private fun checkNextButton(){
        binding.clSignUpGender.visibility = View.VISIBLE
    }

    fun onClickNextBtn(view: View){
        binding.clSignUpGender.setOnClickListener {
            Toast.makeText(this, "다음", Toast.LENGTH_SHORT).show()
        }
    }
}