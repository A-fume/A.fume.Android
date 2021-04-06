package com.afume.afume_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityButtonBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.ui.setting.EditMyInfoActivity
import com.afume.afume_android.ui.setting.EditPasswordActivity
import com.afume.afume_android.ui.signin.SignHomeActivity

class ButtonActivity : AppCompatActivity() {
    lateinit var binding: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_button)
        binding.lifecycleOwner=this

        binding.actButtonTvSignIn.setOnClickListener {
            startActivity(Intent(this, SignHomeActivity::class.java))
        }

        binding.actButtonTvMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.actButtonTvDetail.setOnClickListener {
            startActivity(Intent(this, PerfumeDetailActivity::class.java))
        }
    }
}