package com.afume.afume_android.util

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.NumberPicker

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