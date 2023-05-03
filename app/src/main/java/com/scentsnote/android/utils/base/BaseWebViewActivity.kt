package com.scentsnote.android.utils.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityWebGoogleFormBinding

class BaseWebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebGoogleFormBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_web_google_form)

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

    private fun getUrl() : String{
        return when (intent.getStringExtra("url")) {
            "feedback" -> "https://docs.google.com/forms/d/e/1FAIpQLSfnvvc2O3_1X59lL243vsVXAjy-PIcq6-cgDgrhPph9mCAI1g/viewform"

            "withdrawal" -> "https://docs.google.com/forms/d/e/1FAIpQLSeZL-aslJd_YDgX2kLx31Gra1CXjG6ivaHqyAlko_iDQEVzYg/viewform"

            "tipOff" -> "https://docs.google.com/forms/d/e/1FAIpQLSc9vyuQY9OvrcjCjyWqbhbEmkVi3FyEnoYt2uaBdgmOXsRj4g/viewform"

            "infoReport" -> "https://docs.google.com/forms/d/e/1FAIpQLSfMs2BF3y7urW5u4drHde2C1dOOXCY4SMczjSBeQ0zQF_xotw/viewform"

            else -> ""
        }
    }
}