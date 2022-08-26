package com.scents.note.ui.my

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scents.note.R
import com.scents.note.databinding.ActivityMyInquiryBinding

class MyInquiryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInquiryBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_inquiry)

        val url = getUrl()

        binding.wvMyInquiry.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
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

    fun getUrl() : String{
        val url = intent.getStringExtra("url")
        return if(url == "feedback"){
            "https://docs.google.com/forms/d/e/1FAIpQLSfnvvc2O3_1X59lL243vsVXAjy-PIcq6-cgDgrhPph9mCAI1g/viewform"
        }else{
            "https://docs.google.com/forms/d/e/1FAIpQLSeZL-aslJd_YDgX2kLx31Gra1CXjG6ivaHqyAlko_iDQEVzYg/viewform"
        }
    }
}