package com.afume.afume_android.ui.my

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityMyInquiryBinding

class MyInquiryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInquiryBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_inquiry)

        binding.wvMyInquiry.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        binding.wvMyInquiry.loadUrl("https://forms.gle/XDxCLKGAkzmqGcjR7")
    }

    override fun onBackPressed() {
        if (binding.wvMyInquiry.canGoBack())
        {
            binding.wvMyInquiry.goBack()
        }
        else
        {
            finish()
        }
    }
}