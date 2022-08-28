package com.scentsnote.android.ui.signup

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.scentsnote.android.R

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val imageViewPrivacy = findViewById<TextView>(R.id.btn_back_to_sign_up) as ImageView
        imageViewPrivacy.setOnClickListener {
            finish()
        }

    }

}