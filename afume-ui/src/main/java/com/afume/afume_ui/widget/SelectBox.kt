package com.afume.afume_ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.CompoundButton
import com.afume.afume_ui.R

class SelectBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.AfumeUI_SelectBox_Medium
) : CompoundButton(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {

    init {
        applyAttrs(attrs, defStyleAttr)
    }

    var toggleAble: Boolean = DEFAULT_TOGGLEABLE

    init {
        val defaultBackgroundRes = R.drawable.afume_ui_select_box_background
        val defaultBackgroundTintList = null

        val defaultTextColorStateList =
            context.getColorStateList(R.color.afume_ui_select_box_text_color)
        val defaultClickable = true
        val defaultFocusable = true

        setBackgroundResource(defaultBackgroundRes)
        backgroundTintList = defaultBackgroundTintList

        setTextColor(defaultTextColorStateList)
        isClickable = defaultClickable
        isFocusable = defaultFocusable
    }


    private fun applyAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs == null) {
            return
        }

        context.obtainStyledAttributes(attrs, R.styleable.SelectBox, defStyleAttr, 0).apply {
            toggleAble = getBoolean(R.styleable.SelectBox_toggleAble, DEFAULT_TOGGLEABLE)
            recycle()
        }
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        if (checked) {
            activate()
        } else {
            inactivate()
        }
    }

    override fun toggle() {
        if (!toggleAble) {
            return
        }
        super.toggle()
    }

    override fun isPressed(): Boolean {
        return super.isPressed() && toggleAble
    }

    private fun activate() {
        setTypeface(null, Typeface.BOLD)
    }

    private fun inactivate() {
        setTypeface(null, Typeface.NORMAL)
    }

    companion object {
        const val DEFAULT_TOGGLEABLE = true
    }
}