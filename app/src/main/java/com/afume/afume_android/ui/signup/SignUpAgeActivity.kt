package com.afume.afume_android.ui.signup

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpAgeBinding
import com.afume.afume_android.ui.survey.SurveyActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class SignUpAgeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpAgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_age)
        binding.lifecycleOwner = this


    }

     fun showYearPicker(view: View){
        val yearDialog = AlertDialog.Builder(this).create()
        val yearLayout : LayoutInflater = LayoutInflater.from(this)
        val yearView : View = yearLayout.inflate(R.layout.dialog_year_picker,null)

        val npYear : NumberPicker = yearView.findViewById(R.id.np_year_picker)
        val btnConfirm : Button = yearView.findViewById(R.id.btn_year_picker_confirm)

        npYear.minValue = 1900
        npYear.maxValue = 2020
        npYear.value = 1990

         npYear.wrapSelectorWheel = false
        npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

         setNumberPickerTextColor(npYear,this.resources.getColor(R.color.dark_gray_4a) )
         setNumberPickerDividerColor(npYear, this.resources.getColor(R.color.point_beige))

        btnConfirm.setOnClickListener {
            binding.btnSignUpYearPicker.text = npYear.value.toString()

            yearDialog.dismiss()
            yearDialog.cancel()
        }

        yearDialog.setView(yearView)
        yearDialog.create()
        yearDialog.show()
    }

    private fun setNumberPickerTextColor(picker: NumberPicker, color: Int){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val count = picker.childCount
            for (i in 0..count) {
                val child = picker.getChildAt(i)
                if (child is EditText) {
                    try {
                        child.setTextColor(color)
                        picker.invalidate()
                        var selectorWheelPaintField = picker.javaClass.getDeclaredField("mSelectorWheelPaint")
                        var accessible = selectorWheelPaintField.isAccessible
                        selectorWheelPaintField.isAccessible = true
                        (selectorWheelPaintField.get(picker) as Paint).color = color
                        selectorWheelPaintField.isAccessible = accessible
                        picker.invalidate()
                        var selectionDividerField = picker.javaClass.getDeclaredField("mSelectionDivider")
                        accessible = selectionDividerField.isAccessible
                        selectionDividerField.isAccessible = true
                        selectionDividerField.set(picker, null)
                        selectionDividerField.isAccessible = accessible
                        picker.invalidate()
                    } catch (exception: Exception) {
                        Log.d("test", "exception $exception")
                    }
                }
            }
        } else {
            picker.textColor = color
        }
    }

    private fun setNumberPickerDividerColor(picker: NumberPicker, color: Int)
    {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider")
            {
                pf.isAccessible = true
                try
                {
                    val colorDrawable = ColorDrawable(color)
                    pf.set(picker, colorDrawable)
                } catch (e: IllegalArgumentException)
                {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException)
                {
                    e.printStackTrace()
                } catch (e: IllegalAccessException)
                {
                    e.printStackTrace()
                }
                break
            }
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