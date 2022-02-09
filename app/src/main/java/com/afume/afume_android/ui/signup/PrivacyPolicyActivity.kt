package com.afume.afume_android.ui.signup

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.afume.afume_android.R

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        var textViewPrivacy = findViewById<TextView>(R.id.txt_back_to_sign_up) as TextView
        textViewPrivacy.setOnClickListener {
            finish()
        }

    }

}