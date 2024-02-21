package com.scentsnote.android.utils.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityBaseWebViewBinding
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class BaseWebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityBaseWebViewBinding

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.wvMyInquiry.canGoBack()) binding.wvMyInquiry.goBack()
            else finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_base_web_view)

        val url = getUrl()

        binding.wvMyInquiry.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun getUrl() : String{
        return when (intent.getStringExtra("type")) {
            "feedback" -> "https://docs.google.com/forms/d/e/1FAIpQLSfnvvc2O3_1X59lL243vsVXAjy-PIcq6-cgDgrhPph9mCAI1g/viewform"

            "withdrawal" -> "https://docs.google.com/forms/d/e/1FAIpQLSeZL-aslJd_YDgX2kLx31Gra1CXjG6ivaHqyAlko_iDQEVzYg/viewform"

            "tipOff" -> "https://docs.google.com/forms/d/e/1FAIpQLSc9vyuQY9OvrcjCjyWqbhbEmkVi3FyEnoYt2uaBdgmOXsRj4g/viewform"

            "infoReport" -> "https://docs.google.com/forms/d/e/1FAIpQLSfMs2BF3y7urW5u4drHde2C1dOOXCY4SMczjSBeQ0zQF_xotw/viewform"

            "lowestPrice" -> {
                showToolbar()
                intent.getStringExtra("url") ?: "https://www.naver.com"
            }

            else -> "https://www.naver.com"
        }
    }

    private fun showToolbar() {
        binding.tbWebView.apply {
            root.visibility = View.VISIBLE

            toolbar = R.drawable.icon_btn_cancel
            toolbartxt = "가격 비교"

            toolbarBtn.setOnSafeClickListener {
                finish()
            }
        }
    }
}