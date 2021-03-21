package com.afume.afume_android.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityButtonBinding
import com.afume.afume_android.databinding.ActivityPerfumeDetailBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity

class ButtonActivity : AppCompatActivity() {
    lateinit var binding: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_button)
        binding.lifecycleOwner=this

        binding.actButtonTvMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.actButtonTvDetail.setOnClickListener {
            startActivity(Intent(this, PerfumeDetailActivity::class.java))
        }
    }
}