package com.scentsnote.android.util

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class LayoutedTextView : androidx.appcompat.widget.AppCompatTextView {
        constructor(context: Context?) : super(context!!) {}
        constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
        constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
                context!!,
                attrs,
                defStyle
        ) {
        }

        interface OnLayoutListener {
                fun onLayouted(view: TextView?)
        }

        private var mOnLayoutListener: OnLayoutListener? = null
        fun setOnLayoutListener(listener: OnLayoutListener?) {
                mOnLayoutListener = listener
        }

        override fun onLayout(
                changed: Boolean, left: Int, top: Int, right: Int,
                bottom: Int
        ) {
                super.onLayout(changed, left, top, right, bottom)
                if (mOnLayoutListener != null) {
                        mOnLayoutListener!!.onLayouted(this)
                }
        }
}