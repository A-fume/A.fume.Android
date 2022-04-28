package com.afume.afume_ui_sample.selectbox

import android.os.Bundle
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.appcompat.app.AppCompatActivity
import com.afume.afume_ui_sample.R
import com.afume.afume_ui_sample.common.CommonUtils.findAllViews

class SelectBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_box)

        val container = findViewById<ViewGroup>(R.id.container)
        findAllViews(container, CheckedTextView::class.java).forEach { textView ->
            textView.setOnClickListener {
                textView.toggle()
            }
        }
    }
}