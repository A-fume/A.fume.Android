package com.afume.afume_android.ui.signup

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.NumberPicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpAgeBinding
import com.afume.afume_android.databinding.DialogYearPickerBinding
import com.afume.afume_android.ui.survey.SurveyActivity
import com.afume.afume_android.util.setNumberPickerDividerColor
import com.afume.afume_android.util.setNumberPickerTextColor

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
         val pickerBinding = DataBindingUtil.inflate<DialogYearPickerBinding>(
             layoutInflater,
             R.layout.dialog_year_picker,
             null,
             false
         )

         val builder = AlertDialog.Builder(this)
         builder.setView(pickerBinding.root)

         val yearDialog = builder.create()
         yearDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         yearDialog.window?.setBackgroundDrawableResource(R.drawable.common_dialog_back)

         val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
         yearDialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

         yearDialog.show()

        val npYear : NumberPicker = pickerBinding.npYearPicker

        npYear.minValue = 1900
        npYear.maxValue = 2020
        npYear.value = 1990

         npYear.wrapSelectorWheel = false
        npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

         npYear.setNumberPickerTextColor(R.color.dark_gray_4a)
         npYear.setNumberPickerDividerColor(R.color.point_beige)

         pickerBinding.btnYearPickerConfirm.setOnClickListener {
            binding.btnSignUpYearPicker.text = npYear.value.toString()

            yearDialog.dismiss()
            yearDialog.cancel()
        }
    }

    fun onClickCompleteBtn(view: View){
        val surveyIntent = Intent(this,SurveyActivity::class.java)

        startActivity(surveyIntent)
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}