package com.scentsnote.android.utils.view

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.R
import com.scentsnote.android.databinding.DialogYearPickerBinding
import com.google.android.material.button.MaterialButton
import com.scentsnote.android.utils.etc.Log
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setDrawable
import com.scentsnote.android.utils.extension.setHeight
import java.util.*

class YearPickerDialog(private var ageBtn: MaterialButton) : DialogFragment(), View.OnClickListener {
    lateinit var binding: DialogYearPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogYearPickerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        this.setDrawable()
        setPicker(binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        this.setHeight()
    }

    private fun setPicker(binding: DialogYearPickerBinding) {

        val npYear : NumberPicker = binding.npYearPicker

        npYear.minValue = 1900
        npYear.maxValue = setMaxYear()
        npYear.value = ageBtn.text.toString().toInt()

        npYear.wrapSelectorWheel = false
        npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        npYear.setNumberPickerTextColor(R.color.dark_gray_4a)
        npYear.setNumberPickerDividerColor(R.color.point_beige)

        binding.btnYearPickerConfirm.setOnSafeClickListener {
            ageBtn.text = npYear.value.toString()

            dismiss()
        }
    }

    private fun setMaxYear(): Int{
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }

//    fun getInstance(binding: ActivitySignUpAgeBinding): YearPickerDialog {
//        return YearPickerDialog(binding)
//    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}

fun NumberPicker.setNumberPickerTextColor(color: Int){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val count = this.childCount
        for (i in 0..count) {
            val child = this.getChildAt(i)
            if (child is EditText) {
                try {
                    child.setTextColor(color)
                    this.invalidate()
                    var selectorWheelPaintField = this.javaClass.getDeclaredField("mSelectorWheelPaint")
                    var accessible = selectorWheelPaintField.isAccessible
                    selectorWheelPaintField.isAccessible = true
                    (selectorWheelPaintField.get(this) as Paint).color = color
                    selectorWheelPaintField.isAccessible = accessible
                    this.invalidate()
                    var selectionDividerField = this.javaClass.getDeclaredField("mSelectionDivider")
                    accessible = selectionDividerField.isAccessible
                    selectionDividerField.isAccessible = true
                    selectionDividerField.set(this, null)
                    selectionDividerField.isAccessible = accessible
                    this.invalidate()
                } catch (exception: Exception) {
                    Log.d("test", "exception $exception")
                }
            }
        }
    } else {
        this.textColor = color
    }
}

fun NumberPicker.setNumberPickerDividerColor(color: Int){
    val pickerFields = NumberPicker::class.java.declaredFields
    for (pf in pickerFields) {
        if (pf.name == "mSelectionDivider")
        {
            pf.isAccessible = true
            try
            {
                val colorDrawable = ColorDrawable(color)
                pf.set(this, colorDrawable)
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